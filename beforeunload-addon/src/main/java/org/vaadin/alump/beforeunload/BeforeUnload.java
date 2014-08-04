/**
 * BeforeUnload.java (BeforeUnload)
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

package org.vaadin.alump.beforeunload;

import com.vaadin.server.AbstractExtension;
import com.vaadin.server.Extension;
import com.vaadin.ui.UI;
import org.vaadin.alump.beforeunload.gwt.client.share.BeforeUnloadState;

/**
 * Simple extension that offers access to onBeforeUnload events and this way
 * adding verification dialogs when user is trying to exit/reload the
 * application page. In normal use case you want to call
 * BeforeUnload.setExitVerification(message) to enable verification and then
 * BeforeUnload.unsetExitVerification() to disable it.
 */
public class BeforeUnload extends AbstractExtension {
    protected BeforeUnload() {

    }

    /**
     * Get current "singleton" instance of BeforeUnload
     * @param currentUI UI used to resolve instance
     * @return BeforeUnload instance
     */
    protected static BeforeUnload get(UI currentUI) {
        for(Extension extension : currentUI.getExtensions()) {
            if(extension instanceof BeforeUnload) {
                return (BeforeUnload)extension;
            }
        }
        BeforeUnload beforeUnload = new BeforeUnload();
        beforeUnload.extend(currentUI);
        return beforeUnload;
    }

    /**
     * Get current "singleton instance of BeforeUnload
     * @return BeforeUnload instance
     */
    protected static BeforeUnload get() {
        return get(UI.getCurrent());
    }

    /**
     * Define exit verification shown to user.
     * @param message Message shown to user
     */
    public static void setExitVerification(String message) {
        get().setMessage(message);
    }

    /**
     * Define exit verification shown to user. Alternative version for outside UI context calls.
     * @param message Message shown to user
     * @param currentUI UI used to resolve extension
     */
    public static void setExitVerification(String message, UI currentUI) {
        get(currentUI).setMessage(message);
    }

    /**
     * Check if exit verification has been defined for this extension.
     * @return
     */
    public static boolean hasExitVerification() {
        return getExitVerification() != null;
    }

    /**
     * Check if exit verification has been defined for this extension. Alternative version for outside UI context calls.
     * @param currentUI UI used to resolve extension
     * @return
     */
    public static boolean hasExitVerification(UI currentUI) {
        return getExitVerification(currentUI) != null;
    }

    /**
     * Get exit verification message.
     * @return Message if defined, null if not
     */
    public static String getExitVerification() {
        return get().getMessage();
    }

    /**
     * Get exit verification message. Alternative version for outside UI context calls.
     * @param currentUI UI used to resolve extension
     * @return Message if defined, null if not
     */
    public static String getExitVerification(UI currentUI) {
        return get(currentUI).getMessage();
    }

    /**
     * Unset exit verification defined earlier.
     */
    public static void unsetExitVerification() {
        get().setMessage(null);
    }

    /**
     * Unset exit verification.  Alternative version for outside UI context calls.
     */
    public static void unsetExitVerification(UI currentUI) {
        get(currentUI).setMessage(null);
    }

    protected BeforeUnloadState getState() {
        return (BeforeUnloadState)super.getState();
    }

    /**
     * Define verification message shown to user
     * @param message Message shown to user
     */
    protected void setMessage(String message) {
        getState().message = message;
    }

    /**
     * Get verification message defined.
     * @return Message or null if not defined
     */
    protected String getMessage() {
        return getState().message;
    }
}
