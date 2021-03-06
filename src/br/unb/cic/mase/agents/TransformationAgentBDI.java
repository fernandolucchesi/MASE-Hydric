package br.unb.cic.mase.agents;

//import java.awt.Color;
import java.util.Map;
import java.util.Random;

import br.unb.cic.mase.Util.Printer;
import br.unb.cic.mase.gui.ControllerPanel;
import jadex.bdiv3.annotation.*;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.bridge.IInternalAccess;
import jadex.bridge.component.IArgumentsResultsFeature;
import jadex.bridge.component.IExecutionFeature;
import jadex.bridge.fipa.SFipa;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.types.message.MessageType;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.*;

@Agent
@Arguments({ @Argument(name = "positionX", clazz = Integer.class), @Argument(name = "positionY", clazz = Integer.class),
		@Argument(name = "explorationLevel", clazz = Integer.class), @Argument(name = "index", clazz = Integer.class),
		@Argument(name = "type", clazz = Integer.class) })
@Service
@ProvidedServices(@ProvidedService(type = IDeliberately.class))
public class TransformationAgentBDI implements IDeliberately {
	private final int POOR = 1;
	private final int POOR_COOPERATIVE = 2;
	private final int MIDDLECLASS = 3;
	private final int MIDDLECLASS_COOPERATIVE = 4;
	private final int RICH = 5;
	private final int RICH_COOPERATIVE = 6;

	
	@Agent
	protected IInternalAccess thisAgent;

	@AgentFeature
	protected IBDIAgentFeature bdiFeature;

	@AgentFeature
	protected IExecutionFeature execFeature;

	@Belief
	private int positionX;
	@Belief
	private int positionY;
	@Belief
	private int explorationLevel;
	@Belief
	private double currentExploration;
	@Belief
	private int type;
	@Belief
	private int index;
	@Belief
	private boolean ready;

	@AgentCreated
	public void created() {
		Printer.print("created: " + this);

		positionX = (Integer) thisAgent.getComponentFeature(IArgumentsResultsFeature.class).getArguments()
				.get("positionX");
		positionY = (Integer) thisAgent.getComponentFeature(IArgumentsResultsFeature.class).getArguments()
				.get("positionY");
		explorationLevel = (Integer) thisAgent.getComponentFeature(IArgumentsResultsFeature.class).getArguments()
				.get("explorationLevel");
		index = (Integer) thisAgent.getComponentFeature(IArgumentsResultsFeature.class).getArguments().get("index");
		type = (Integer) thisAgent.getComponentFeature(IArgumentsResultsFeature.class).getArguments().get("type");
//		currentExploration = explorationLevel;
		
		
		ControllerPanel.getInstance().addTAIdentification(index, thisAgent.getComponentIdentifier());
	}

	@AgentMessageArrived
	public void messageArrived(Map<String, Object> msg, final MessageType mt) {
		if (msg != null) {
			Printer.print("" + index);
			if (((String) msg.get(SFipa.PERFORMATIVE)).equals(SFipa.REQUEST)) {
				ready = true;
			}
		}
	}

	@Goal
	public class Deliberate {

	}

	@Goal
	public class UseWater {

	}

	@Goal
	public class SaveWater {

	}

	public int getPositionX(){
		return this.positionX;
	}
	
	public int getPositionY(){
		return this.positionY;
	}
	
	public int getType(){
		return this.type;
	}
	
	private synchronized int generateRandom()
	{
		Random rand = new Random();
//		System.out.println("Random = " + rand.nextInt(100));
		return (rand.nextInt(100));
		
	}
//	
//	private boolean checkNeighbourhood (int neighbourhood) 
//	{
//		//System.out.println(neighbourhood);
//		if (neighbourhood < -3)
//		{
//			bdiFeature.dispatchTopLevelGoal(new UseWater()).get();
//			return true;
//		} else if (neighbourhood > 3)
//		{
//			bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();
//			return true;
//		} else if (neighbourhood < -2 && (type != RICH && type != RICH_COOPERATIVE))
//		{
//			bdiFeature.dispatchTopLevelGoal(new UseWater()).get();
//			return true;
//		} else if (neighbourhood > 2 && (type != RICH && type != RICH_COOPERATIVE))
//		{
//			bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();
//			return true;
//		} else if (neighbourhood < -1 && (type != MIDDLECLASS && type != MIDDLECLASS_COOPERATIVE) && (type != RICH && type != RICH_COOPERATIVE))
//		{
//			bdiFeature.dispatchTopLevelGoal(new UseWater()).get();
//			return true;
//		} else if (neighbourhood > 1 && (type != MIDDLECLASS && type != MIDDLECLASS_COOPERATIVE) && (type != RICH && type != RICH_COOPERATIVE))
//		{
//			bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();
//			return true;
//		}else {
//			return false;
//		}
//	}
//	
	private void checkTax()
	{
		int randomNumber = generateRandom();
		// + CAMPANHA EDUCATIVA - TAXA
		if (ControllerPanel.getInstance().isR1Active()) {
			if (type == POOR || type == POOR_COOPERATIVE)
			{
				if (randomNumber<75)
				{
					currentExploration -= 6;
//					bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();	
				}
				else {
					currentExploration += 0;
//					bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
			}
			else if (type == MIDDLECLASS || type == MIDDLECLASS_COOPERATIVE)
			{
				if (randomNumber<70)
				{
					currentExploration -= 4.5;
//					bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();	
				}
				else {
					currentExploration += 0;
//					bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
			}
			else if (type == RICH || type == RICH_COOPERATIVE)
			{
				if (randomNumber<18)
				{
					currentExploration -= 3.48;
//					bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();	
				}
				else {
					currentExploration += 0;
//					bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
			}
		}
	}
	
	
	private  void checkEducation()
	{
		int randomNumber = generateRandom();
		// + CAMPANHA EDUCATIVA - TAXA
		if (ControllerPanel.getInstance().isR2Active()) {
			if (type == POOR || type == POOR_COOPERATIVE)
			{
				if (randomNumber<87)
				{
					currentExploration -= 4.2;
//					bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();	
				}
				else {
					currentExploration += 0;
//					bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
			}
			else if (type == MIDDLECLASS || type == MIDDLECLASS_COOPERATIVE)
			{
				if (randomNumber<81)
				{
					currentExploration -= 4.19;
//					bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();	
				}
				else {
					currentExploration += 0;
//					bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
			}
			else if (type == RICH || type == RICH_COOPERATIVE)
			{
				if (randomNumber<76)
				{
					currentExploration -= 3.41;
//					bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();	
				}
				else {
					currentExploration += 0;
//					bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
			}
		}
		// + CAMPANHA EDUCATIVA + TAXA
		else if (ControllerPanel.getInstance().isR2Active() && ControllerPanel.getInstance().isR1Active())
		{
			if (type == RICH || type == RICH_COOPERATIVE)
			{
				if (randomNumber<76)
				{
					currentExploration -= 3.41;
//					bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();	
				}
				else {
					currentExploration += 0;
//					bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
			}
		}
		
	}
	
	
	private  void checkRain (boolean drySeason) {
		int randomNumber = generateRandom();
		if (drySeason == true)
		{	
			if (type == POOR || type == POOR_COOPERATIVE)
			{
				if (randomNumber<78)
				{
					currentExploration += 0.3;
//					bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();	
				}
				else if (randomNumber<93)
				{
					currentExploration += 0;
//					bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
				else {
					currentExploration -= 0.6;
//					bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
			}
			
			else if (type == MIDDLECLASS || type == MIDDLECLASS_COOPERATIVE)
			{
				if (randomNumber<77)
				{
					currentExploration += 0.96;
//					bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();	
				}
				else if (randomNumber<89)
				{
					currentExploration += 0;
//					bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
				else {
					currentExploration -= 1.41;
//					bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
				
			}
			
			else if (type == RICH || type == RICH_COOPERATIVE)
			{
				if (randomNumber<76)
				{
					currentExploration += 1.54;
//					bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();	
				}
				else if (randomNumber<88)
				{
					currentExploration += 0;
//					bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
				else {
					currentExploration -= 0.5;
//					bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
				
			}
			
		}
		else
		{
			if (type == POOR || type == POOR_COOPERATIVE)
			{
				if (randomNumber<8)
				{
					currentExploration += 0.6;
					//bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();	
				}
				else if (randomNumber<22)
				{
					currentExploration += 0;
					//bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
				else {
					currentExploration -= 0.3;
					//bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
			}
			
			else if (type == MIDDLECLASS || type == MIDDLECLASS_COOPERATIVE)
			{
				if (randomNumber<10)
				{
					currentExploration += 0.93;
					//bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();	
				}
				else if (randomNumber<23)
				{
					currentExploration += 0;
					//bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
				else {
					currentExploration -= 0.92;
					//bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
				
			}
			
			else if (type == RICH || type == RICH_COOPERATIVE)
			{
				if (randomNumber<12)
				{
					currentExploration += 0.27;
					//bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();	
				}
				else if (randomNumber<24)
				{
					currentExploration += 0;
				//	bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
				else {
					currentExploration -= 0.92;
					//bdiFeature.dispatchTopLevelGoal(new UseWater()).get();	
				}
			}
			
		}
	}
	
	public IFuture<Void> deliberate(boolean drySeason) {
//		int aux = ControllerPanel.getInstance().selectedPriority;
		
		Printer.print("transformationAgent" + index + " is deliberating...");
		
		if (type == POOR || type == POOR_COOPERATIVE) {
			currentExploration = 16.2;
			checkTax();
			if (currentExploration == 16.2)
			{
				checkEducation();
			}
			checkRain (drySeason);
		}
		else if (type == MIDDLECLASS || type == MIDDLECLASS_COOPERATIVE) {
			currentExploration = 17.5;
			checkTax();
			if (currentExploration == 17.5)
			{
				checkEducation();
			}
			checkRain (drySeason);
			
		}
		else {
			currentExploration = 17.4;
			checkEducation();
			if (currentExploration == 17.5)
			{
				checkTax();
			}
			checkRain (drySeason);
		}
		
		
		
		
		if ( 
			((type == POOR || type == POOR_COOPERATIVE) &&  currentExploration < 16.2) ||
			((type == MIDDLECLASS || type == MIDDLECLASS_COOPERATIVE) && currentExploration < 17.5) ||
			((type == RICH || type == RICH_COOPERATIVE) && currentExploration < 17.4) 
			) 	bdiFeature.dispatchTopLevelGoal(new SaveWater()).get();
		else bdiFeature.dispatchTopLevelGoal(new UseWater()).get();
			
		return new Future<Void>();
	}

	@Plan(trigger = @Trigger(goals = (UseWater.class)))
	public void useWaterNormally() {
		Printer.print("transformationAgent" + index + " decided to use water normally.");
		if (this.type == POOR_COOPERATIVE && currentExploration >= 16.2) {
			this.type = POOR;
		} else if (this.type == MIDDLECLASS_COOPERATIVE && currentExploration >= 17.5){
			this.type = MIDDLECLASS;
		} else if (this.type == RICH_COOPERATIVE && currentExploration >= 17.4){
			this.type = RICH;
		}
		ControllerPanel.getInstance().diminishWaterLevel(currentExploration);
		ControllerPanel.getInstance().updateDataForReport(currentExploration, this.type);
//		System.out.println(currentExploration);
	}

	@Plan(trigger = @Trigger(goals = (SaveWater.class)))
	public void useWaterSparingly() {
		Printer.print("transformationAgent" + index + " decided to use water sparingly.");
		if (this.type == POOR) {
			this.type = POOR_COOPERATIVE;
		} else if (this.type == MIDDLECLASS){
			this.type = MIDDLECLASS_COOPERATIVE;
		} else if (this.type == RICH)
		{
			this.type = RICH_COOPERATIVE;
		}
		ControllerPanel.getInstance().diminishWaterLevel(currentExploration);
		ControllerPanel.getInstance().updateDataForReport(currentExploration, this.type);
//		System.out.println(currentExploration);
	}

	

}
