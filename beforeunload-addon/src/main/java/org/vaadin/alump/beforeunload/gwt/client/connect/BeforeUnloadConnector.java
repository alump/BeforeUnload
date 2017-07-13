/**
 * BeforeUnloadConnector.java (BeforeUnload)
 *
 * Copyright 2013 Vaadin Ltd, Sami Viitanen <alump@vaadin.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.vaadin.alump.beforeunload.gwt.client.connect;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.shared.ui.Connect;
import org.vaadin.alump.beforeunload.gwt.client.share.BeforeUnloadClientRpc;
import org.vaadin.alump.beforeunload.gwt.client.share.BeforeUnloadState;

import java.util.Date;

/**
 * Connector for BeforeUnload extension
 */
@Connect(org.vaadin.alump.beforeunload.BeforeUnload.class)
public class BeforeUnloadConnector extends AbstractExtensionConnector {

    private HandlerRegistration winCloseRegistration;
    private long temporaryDisabled = getTime();
    private static boolean permanentlyDisabled = false;

    private static BeforeUnloadConnector instance;

    private final BeforeUnloadClientRpc clientRpc = milliSecs -> {
        BeforeUnloadConnector.this.setTemporaryDisabled(milliSecs);
    };

    private final Window.ClosingHandler closingHandler = closingEvent -> {
        if (isEnabled()) {
            closingEvent.setMessage(getMessage());
        } else {
            closingEvent.setMessage(null);
        }
    };

    protected void init() {
        super.init();
        registerRpc(BeforeUnloadClientRpc.class, clientRpc);
        instance = this;
        winCloseRegistration = Window.addWindowClosingHandler(closingHandler);
    }

    public void onUnregister() {
        if (instance == this) {
            instance = null;
        }

        if (winCloseRegistration != null) {
            winCloseRegistration.removeHandler();
            winCloseRegistration = null;
        }

        super.onUnregister();
    }

    @Override
    protected void extend(ServerConnector serverConnector) {
        //ignore
    }

    public BeforeUnloadState getState() {
        return (BeforeUnloadState) super.getState();
    }

    /**
     * Way to temporary disable verification. This can be used to avoid
     * error when forcing page reload on client side (eg. connection
     * error at ApplicationConnection).
     *
     * @param millisecs How long exit verification should be disabled
     *                  in milliseconds.
     */
    public static void disableTemporary(long millisecs) {
        if (instance != null) {
            instance.setTemporaryDisabled(millisecs);
        }
    }

    protected void setTemporaryDisabled(long millisecs) {
        temporaryDisabled = getTime() + millisecs;
    }

    /**
     * Way to permanently disable verification.
     * This can be used to avoid error when user manually reloading after
     * a connection error, or session timeout error.
     */
    public static void disablePermanently() {
        permanentlyDisabled = true;
    }

    /**
     * Check if before unload warning is enabled
     *
     * @return true if enabled
     */
    public boolean isEnabled() {
        return getState().enabled && !permanentlyDisabled && getTime() >= temporaryDisabled;
    }

    private long getTime() {
        return new Date().getTime();
    }

    protected String getMessage() {
        return getState().message;
    }

}
