package com.demo;

import javax.servlet.annotation.WebServlet;

import org.vaadin.teemu.wizards.Wizard;
import org.vaadin.teemu.wizards.WizardStep;
import org.vaadin.teemu.wizards.event.WizardCancelledEvent;
import org.vaadin.teemu.wizards.event.WizardCompletedEvent;
import org.vaadin.teemu.wizards.event.WizardProgressListener;
import org.vaadin.teemu.wizards.event.WizardStepActivationEvent;
import org.vaadin.teemu.wizards.event.WizardStepSetChangedEvent;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{
	private String deviceChoice;
	private Label errorMessage;
	private Label simpleMessage = new Label("Simple message");
	Wizard wizard = new Wizard();
	Window window = new Window();
	
    public String getDeviceChoice() {
		return deviceChoice;
	}

	public void setDeviceChoice(String deviceChoice) {
		this.deviceChoice = deviceChoice;
	}

	@WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);  
        
        errorMessage = new Label("Error message..");
        errorMessage.setVisible(false);
        simpleMessage.setVisible(true);
        
        wizard.addStep(new Step1());
        wizard.addStep(new Step2());
        wizard.addStep(new Step3());

        layout.addComponent(wizard);
    	
    	//window.setContent(layout);
    	//window.setHeight(500, Unit.PIXELS);
    	//addWindow(window);
    }

    private class Step1 implements WizardStep{

		@Override
		public String getCaption() {
			return "Step 1";
		}

		@Override
		public Component getContent() {
			VerticalLayout content = new VerticalLayout();

			wizard.addListener(new WizardProgressListener() {
				
				@Override
				public void wizardCompleted(WizardCompletedEvent event) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void wizardCancelled(WizardCancelledEvent event) {
					wizard.setVisible(false);
                    close();
				}
				
				@Override
				public void stepSetChanged(WizardStepSetChangedEvent event) {
					event.getWizard().getNextButton().setEnabled(false);
					
				}
				
				@Override
				public void activeStepChanged(WizardStepActivationEvent event) {
				
				}
			});
			
            final OptionGroup optiongroup = new OptionGroup();
            optiongroup.setMultiSelect(false);
            optiongroup.addItem("Камера");
            optiongroup.addItem("Z-Wave");
            optiongroup.setImmediate(true);

            optiongroup.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent event) {
                    if (optiongroup.getValue().equals("Камера")){
                        setDeviceChoice("Камера");
                        wizard.getNextButton().setEnabled(true);
                    } else if (optiongroup.getValue().equals("Z-Wave")) {
                        setDeviceChoice("Z-Wave");
                        wizard.getNextButton().setEnabled(true);
                    } else if(optiongroup.getValue().equals("")){
                        errorMessage.setValue("Необходимо выбрать тип оборудования");
                        errorMessage.setVisible(true);
                        wizard.getNextButton().setEnabled(false);
                    }
                }
            });
            
            content.addComponent(optiongroup);
            content.addComponent(errorMessage);
            content.addComponent(simpleMessage);
            
            /*wizard.getCancelButton().addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {

                    wizard.setVisible(false);
                    close();
                }

            });*/
            return content;
		}

		@Override
		public boolean onAdvance() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean onBack() {
			// TODO Auto-generated method stub
			return false;
		}
    	
    }
       
    private class Step2 implements WizardStep{

		@Override
		public String getCaption() {
			// TODO Auto-generated method stub
			return "Step 2";
		}

		@Override
		public Component getContent() {
			VerticalLayout content = new VerticalLayout();

			wizard.addListener(new WizardProgressListener() {
				
				@Override
				public void wizardCompleted(WizardCompletedEvent event) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void wizardCancelled(WizardCancelledEvent event) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void stepSetChanged(WizardStepSetChangedEvent event) {
					
				}
				
				@Override
				public void activeStepChanged(WizardStepActivationEvent event) {
					event.getWizard().getCancelButton().setEnabled(false);
					event.getWizard().getBackButton().setEnabled(false);
				}
			});
				
            Label label = new Label("Step2");
            
            content.addComponent(label);
            content.addComponent(errorMessage);
            return content;
		}

		@Override
		public boolean onAdvance() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onBack() {
			// TODO Auto-generated method stub
			return false;
		}
    	
    }
    
    private class Step3 implements WizardStep{

		@Override
		public String getCaption() {
			// TODO Auto-generated method stub
			return "Step 3";
		}

		@Override
		public Component getContent() {
			VerticalLayout content = new VerticalLayout();

            Label label = new Label("Step3");
            
            content.addComponent(label);
            content.addComponent(errorMessage);
            return content;
		}

		@Override
		public boolean onAdvance() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onBack() {
			// TODO Auto-generated method stub
			return false;
		}
    	
    }
}
