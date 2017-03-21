package org.vaadin.alump.beforeunload.demo;

import com.vaadin.annotations.Title;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.alump.beforeunload.BeforeUnload;

/**
 * BeforeUnload extension Demo UI
 */
@Title("BeforeUnload Demo")
public class BeforeUnloadDemoUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        // In this example before upload will be enabled by default
        BeforeUnload.enable();

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth(100, Unit.PERCENTAGE);
        layout.setSpacing(true);
        layout.setMargin(true);
        setContent(layout);

        Label header = new Label("BeforeUnload Vaadin add-on");
        header.addStyleName(ValoTheme.LABEL_H1);

        CheckBox checkbox = new CheckBox("Verify that user really wants to leave the page",
                BeforeUnload.isEnabled());
        checkbox.addValueChangeListener(event -> BeforeUnload.setEnabled(event.getValue()));

        Button disableForTwoSeconds = new Button("Temporary disable for 2 seconds on client side",
                e -> BeforeUnload.temporaryDisable(2000L));

        layout.addComponents(header,
                new Label("Exit verification message is shown when you leave or reload the page."),
                checkbox,
                disableForTwoSeconds,
                new Link("Project at GitHub", new ExternalResource("https://github.com/alump/BeforeUnLoad")));
    }
}
