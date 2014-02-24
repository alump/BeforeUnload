package org.vaadin.alump.beforeunload.demo;

import com.vaadin.annotations.Title;
import com.vaadin.data.Property;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.vaadin.alump.beforeunload.BeforeUnload;

/**
 * BeforeUnload extension Demo UI
 */
@Title("BeforeUnload Demo")
public class BeforeUnloadDemoUI extends UI {

    private TextField messageField;
    private CheckBox checkbox;
    public final static String DEFAULT_MESSAGE = "Please do not leave! I'm lonely :(";

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.setSpacing(true);
        layout.setMargin(true);
        setContent(layout);

        BeforeUnload.setExitVerification(DEFAULT_MESSAGE);

        Label guide = new Label("Exit verification message is shown when you leave or reload the page.");
        guide.setWidth("100%");
        layout.addComponent(guide);

        messageField = new TextField();
        messageField.setImmediate(true);
        messageField.setValue(BeforeUnload.getExitVerification());
        messageField.setWidth("100%");
        messageField.setCaption("Exit verification message:");
        messageField.setInputPrompt("Write verification message here");
        messageField.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                if(checkbox.getValue()) {
                    BeforeUnload.setExitVerification((String)valueChangeEvent.getProperty().getValue());
                }
            }
        });
        layout.addComponent(messageField);

        checkbox = new CheckBox("Enabled");
        checkbox.setImmediate(true);
        checkbox.setValue(BeforeUnload.hasExitVerification());
        checkbox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                boolean value = (Boolean)valueChangeEvent.getProperty().getValue();
                if(value) {
                    BeforeUnload.setExitVerification(messageField.getValue());
                } else {
                    BeforeUnload.unsetExitVerification();
                }
            }
        });
        layout.addComponent(checkbox);
    }
}
