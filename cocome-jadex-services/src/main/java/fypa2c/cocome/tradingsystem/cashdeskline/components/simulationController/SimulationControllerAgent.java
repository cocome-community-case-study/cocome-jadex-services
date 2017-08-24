package fypa2c.cocome.tradingsystem.cashdeskline.components.simulationController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.Random;

import fypa2c.cocome.tradingsystem.cashdeskline.components.EventAgent;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cardReaderController.ICardReaderControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.ICashBoxControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashBoxController.PaymentMode;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskApplication.ICashDeskApplicationService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.cashDeskGUI.ICashDeskGUIService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.eventBus.IEventBusService;
import fypa2c.cocome.tradingsystem.cashdeskline.components.logging.LogWriter;
import fypa2c.cocome.tradingsystem.cashdeskline.components.scannerController.IScannerControllerService;
import fypa2c.cocome.tradingsystem.cashdeskline.events.AddLastScannedProductAgainEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.CashAmountEnteredEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.IEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.PaymentModeSelectedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.ProductBarcodeScannedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.RemoveLastScannedProductEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.RunningTotalChangedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleFinishedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleStartedEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.events.SaleSuccessEvent;
import fypa2c.cocome.tradingsystem.cashdeskline.transferObjects.StockItemTO;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.component.IProvidedServicesFeature;
import jadex.commons.Boolean3;
import jadex.commons.IFilter;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IIntermediateResultListener;
import jadex.commons.future.ISubscriptionIntermediateFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.Implementation;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

import org.apache.commons.*;
import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;

import cern.jet.random.engine.RandomGenerator;

/**
 * This agent is a simulation module for one cashdesk. 
 * It simulates costumers using the cashdesk in different 
 * ways (cash or card payment) and for an different 
 * amount of any products. The probability distribution
 * of the different events must be defined in the simulation properties file (//TODO create simulation properties file)
 *
 * @author Florian Abt
 */
@Agent(keepalive = Boolean3.TRUE)
@ProvidedServices({
	@ProvidedService(name="SimulationController",type=ISimulationControllerService.class, implementation=@Implementation(SimulationControllerService.class))//,
})
@RequiredServices({
	@RequiredService(name="cashBoxController", type=ICashBoxControllerService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
	@RequiredService(name="cashDeskApplication", type=ICashDeskApplicationService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
	@RequiredService(name="scannerController", type=IScannerControllerService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM)),
	@RequiredService(name="cashDeskGUI", type=ICashDeskGUIService.class, binding=@Binding(scope=RequiredServiceInfo.SCOPE_PLATFORM))//,
})
public class SimulationControllerAgent extends EventAgent {
	
	private SimulationGUI gui = new SimulationGUI();
	
	private volatile boolean isSimulationStopped = true;
	
	//the speed of simulation gives the interpretation of the system about one second 
	//1 means one second in real time is one second in simulation time
	//10 means ten seconds in real time are one second in simulation time
	//maximum is 1000
	private int speedOfSimulation = 500;
	
	//This thread simulates the sale processes (Product scan process and payment of all costumers)
	private Thread simulationThread;
	
	//The running total of the actual sale process
	private double runningTotal = 0.0;
	
	//costumer arrivals:
		//A Thread that processes the arrival of new costumers waiting in the queue in front of the cash desk
		private Thread costumerArrivalsThread;
		//Counts the waiting  costumers
		private volatile int waitingCostumers;
		//Distribution of arrivals of new Costumers at the cash desk (mean, standard deviation) both values are in seconds 
		private NormalDistribution arrivalDistribution = new NormalDistribution(300, 90);
	//product scan:
		//Distribution of the amount of products that a costumer wants to buy (mean, standard deviation) both values are the amount of different products.
		private NormalDistribution productsAmountDistribution = new NormalDistribution(10, 2);
		//Distribution of the time needed to scan a product (mean, standard deviation) both values are in seconds 
		private NormalDistribution scanTimeDistribution = new NormalDistribution(2, 0.3);
		//Probability of the decision whether the product is added again (+1 Button)
		private double plusProbability = 0.02;
		//Probability of the decision whether the scan of the last product failed and it must be removed (-1 Button)
		private double minusProbability = 0.01;
	//payment:
		//Probability of the cash payment, otherwise card payment
		private double cashPaymentProbability = 0.7;
		//Distribution of the time needed to pay (for this simulation there is no difference between cash and card payment, both using the same distribution), values: (mean, standard deviation) in seconds 
		private NormalDistribution paymentTime = new NormalDistribution(30, 9);
		
		
	//product range: an array in which all products are stored during system start
	private ArrayList<Long> productRange;
	//RandomGenerator for the random product selection out of the product range 
	private Random randomGenerator = new Random();
	
	
	@Agent
	protected IInternalAccess agent;
	
	@AgentCreated
	public IFuture<Void> creation()
	{
		super.creation();
		
		setLog("SimulationControllerAgent");
		
		//initialize productRange
		productRange = new ArrayList<Long>();
		StockItemTO result = null;
		try {
			FileReader in = new FileReader("StockItems.txt");
		    BufferedReader br = new BufferedReader(in);
		    
		    //to jump over the first line of column names
		    br.readLine();
		    String line;
		    while((line = br.readLine()) != null) {
		    	String[] product = line.split(";");
		    	result = new StockItemTO(Long.parseLong(product[0]), product[1], Double.parseDouble(product[2]), Long.parseLong(product[3]), Long.parseLong(product[4]), Long.parseLong(product[5]));		    	
		    	productRange.add(result.getId());
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return Future.DONE;
	}
	
	@AgentBody
	public void body(){
		
		initializeSimulationGUI();
		
		subscribeToEvents();
		
	}
	
	public void initializeSimulationGUI() {
		gui.getStartButton().setEnabled(true);
		gui.getStopButton().setEnabled(false);
		
		gui.getStartButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Begin a knew logging folder with empty log files for this simulation
				LogWriter.startNewLogFolder();
				
				printInfoLog("Start simulation");
				isSimulationStopped = false;
				
				gui.getStartButton().setEnabled(false);
				gui.getStopButton().setEnabled(true);
				
				//Set waiting Costumers in front of the cash desk to 0
				waitingCostumers = 0;
				costumerArrivalsThread = new Thread(new Runnable() {
					
					
					
					@Override
					public void run() {
						//Run this Thread until it was interrupted by the stopButton
						while(!isSimulationStopped) {
							//Get next arrival time of a new costumer
							long timeTillNextCostumer = (long)(arrivalDistribution.sample()*1000/speedOfSimulation);
							timeTillNextCostumer = (timeTillNextCostumer >=0 ) ? timeTillNextCostumer : 0;
							printInfoLog("Next costumer arrives in "+(timeTillNextCostumer/1000*speedOfSimulation)+" seconds.");
							//Wait until this time
							try {
								Thread.sleep(timeTillNextCostumer);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							//Add new costumer to waitingCostumers
							waitingCostumers++;
							printInfoLog("New costumer arrives. "+waitingCostumers+" are waiting now.");
						}
					}
				});
				
				costumerArrivalsThread.start();
				
				simulationThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						while(!isSimulationStopped) {
							//1. Start sale if a costumer is waiting (waitingCostumers)
							if(waitingCostumers>0) {
								waitingCostumers--;
								((ICashBoxControllerService)requiredServicesFeature.getRequiredService("cashBoxController").get()).sendSaleStartedEvent(new SaleStartedEvent(getLog()));
								printInfoLog("New sale started");
								//Generate amount of products (the products will be chosen randomly from the product range)
								long amountOfProducts = Math.round(productsAmountDistribution.sample());
								amountOfProducts = (amountOfProducts <= 0) ? 1 : amountOfProducts;
								printInfoLog("An amount of "+amountOfProducts+" products is given for this sale.");
								//2. Scan products
								while(amountOfProducts > 0) {
									//wait time until scan event fired (time of a scan in seconds)
									long scanTime = (long)(scanTimeDistribution.sample()*1000/speedOfSimulation);
									scanTime = (scanTime<0) ? 0 : scanTime;
									//Wait until this time
									try {
										Thread.sleep(scanTime);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									//Scan product
									long barcode = productRange.get(randomGenerator.nextInt(productRange.size()));
									ProductBarcodeScannedEvent event = new ProductBarcodeScannedEvent(barcode, getLog());
									((IScannerControllerService)requiredServicesFeature.getRequiredService("scannerController").get()).sendProductBarCodeScannedEvent(event);
									amountOfProducts--;
									printInfoLog("product "+barcode+" scanned");
									if(amountOfProducts > 0) {
										if(randomGenerator.nextInt(99) < plusProbability*100) {
											AddLastScannedProductAgainEvent eventAddLast = new AddLastScannedProductAgainEvent(getLog());
											((ICashDeskGUIService)requiredServicesFeature.getRequiredService("cashDeskGUI").get()).sendAddLastScannedProductAgainEvent(eventAddLast);
											amountOfProducts--;
											printInfoLog("Last scanned product added again.");
										}
										if(randomGenerator.nextInt(99) < minusProbability*100) {
											RemoveLastScannedProductEvent eventRemoveLast = new RemoveLastScannedProductEvent(getLog());
											((ICashDeskGUIService)requiredServicesFeature.getRequiredService("cashDeskGUI").get()).sendRemoveLastScannedProductEvent(eventRemoveLast);
											amountOfProducts++;
											printInfoLog("Last scanned product removed");
										}
									}
								}
								printInfoLog("All products are scanned");
								((ICashBoxControllerService)requiredServicesFeature.getRequiredService("cashBoxController").get()).sendSaleFinishedEvent(new SaleFinishedEvent(getLog()));
								printInfoLog("Choose payment mode");
								//3. Choose payment mode
								if(randomGenerator.nextInt(99) < cashPaymentProbability*100) {
									printInfoLog("Cash payment was chosen. Costumer must pay "+runningTotal+"€");
									//CashPayment
									((ICashBoxControllerService)requiredServicesFeature.getRequiredService("cashBoxController").get()).sendPaymentModeEvent(new PaymentModeSelectedEvent(PaymentMode.CASH, getLog()));
									//wait until payed
									long payTime = (long)(paymentTime.sample()*1000/speedOfSimulation);
									payTime = (payTime < 0) ? 0 : payTime;
									try {
										Thread.sleep(payTime);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									//In this simulation the first entered amount of money is always higher than the total running
									((ICashBoxControllerService)requiredServicesFeature.getRequiredService("cashBoxController").get()).sendCashAmountEnteredEvent(new CashAmountEnteredEvent(runningTotal+1, true, getLog()));
									printInfoLog("Costumer payed!");
								}
								else {
									printInfoLog("Card payment was chosen");
									//CardPayment
									((ICashBoxControllerService)requiredServicesFeature.getRequiredService("cashBoxController").get()).sendPaymentModeEvent(new PaymentModeSelectedEvent(PaymentMode.CREDIT_CARD, getLog()));
									//wait until payed
									long payTime = (long)(paymentTime.sample()*1000/speedOfSimulation);
									payTime = (payTime < 0) ? 0 : payTime;
									try {
										Thread.sleep(payTime);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								//Payment finished
								printInfoLog("Payment finished");
								((ICashDeskApplicationService)requiredServicesFeature.getRequiredService("cashDeskApplication").get()).sendSaleSuccessEvent(new SaleSuccessEvent(getLog()));
							}
						}
					}
				});
				
				simulationThread.start();
				
			}			
		});
		
		gui.getStopButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				printInfoLog("Stop simulation");				
				gui.getStopButton().setEnabled(false);
				// Stop simulationThreads
				isSimulationStopped = true;
				//Wait until both threads finished their work
				while(costumerArrivalsThread.isAlive() || simulationThread.isAlive()) {}
				printInfoLog("Simulation stopped");
				gui.getStartButton().setEnabled(true);
			}
		});
		
	}

	/**
	 * The agent subscribes to all events.
	 */
	public void subscribeToEvents(){
		//subscribe
		ISubscriptionIntermediateFuture<IEvent> sifuture = ((IEventBusService)requiredServicesFeature.getRequiredService("eventBus").get()).subscribeToEvents(null);
		
		//waiting for Events
		sifuture.addIntermediateResultListener(new IIntermediateResultListener<IEvent>() {
			
			@Override
			public void exceptionOccurred(Exception exception) {
				printInfoLog("Exception occurred");
				exception.printStackTrace();
			}
			
			@Override
			public void resultAvailable(Collection<IEvent> result) {
				printInfoLog("Received IEvent collection");
			}
			
			@Override
			public void intermediateResultAvailable(IEvent result) {
				//printInfoLog("Received "+result.getClass().getName());
				if(result instanceof RunningTotalChangedEvent) {
					runningTotal = ((RunningTotalChangedEvent)result).getRunningTotal();
				}
			}
			
			@Override
			public void finished() {
				printInfoLog("IntermediateResult finished");
			}
		});
	}
	
	/**
	 * to get the Service of this agent for access to all provided services
	 * @return
	 */
	private ISimulationControllerService getServiceProvided()
	{
		return (ISimulationControllerService)agent.getComponentFeature(IProvidedServicesFeature.class).getProvidedService("SimulationController");
	}
}
