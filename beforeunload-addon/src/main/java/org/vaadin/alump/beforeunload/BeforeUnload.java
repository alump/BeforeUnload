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
import org.vaadin.alump.beforeunload.gwt.client.share.BeforeUnloadClientRpc;
import org.vaadin.alump.beforeunload.gwt.client.share.BeforeUnloadState;

import java.util.Objects;
import java.util.Optional;

/**
 * Simple extension that offers access to onBeforeUnload events and this way adding verification dialogs when user is
 * trying to exit/reload the application page. In normal use case you want to call BeforeUnload.enable(); to enable
 * BeforeUnload, and then later (when it is fine to leave the site) disable it with BeforeUnload.disable();
 */
public class BeforeUnload extends AbstractExtension {
    protected BeforeUnload() {

    }

    /**
     * Optionally get current existing instance of BeforeUnload
     * @return empty if not found or if UI context could not be resolved
     */
    protected static Optional<BeforeUnload> optionalGet() {
        return optionalGet(UI.getCurrent());
    }

    /**
     * Optionally get current existing instance of BeforeUnload
     * @return empty if not found
     */
    protected static Optional<BeforeUnload> optionalGet(UI currentUI) {
        if(currentUI == null) {
            return Optional.empty();
        }

        for(Extension extension : currentUI.getExtensions()) {
            if(extension instanceof BeforeUnload) {
                return Optional.of((BeforeUnload)extension);
            }
        }

        return Optional.empty();
    }

    /**
     * Get instance message. This method is marked protected as most of modern browsers do not anymore show the
     * message defined on before unload API, so offering it on API would confuse users. If you still want to access this
     * just extend class and make this public.
     * @return Message
     */
    protected Optional<String> getInstanceMessage() {
        return Optional.ofNullable(getState(false).message);
    }

    /**
     * Set instance message. This method is marked protected as most of modern browsers do not anymore show the
     * message defined on before unload API, so offering it on API would confuse users. If you still want to access this
     * just extend class and make this public.
     * @param message
     */
    protected void setInstanceMessage(String message) {
        getState().message = Objects.requireNonNull(message);
    }

    /**
     * Get current "singleton" instance of BeforeUnload
     * @param currentUI UI used to resolve instance
     * @return BeforeUnload instance
     */
    protected static BeforeUnload get(UI currentUI) throws IllegalStateException {
        if(currentUI == null) {
            throw new IllegalStateException("Current UI not defined");
        }

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
     * Check if instance is enabled or disabled
     * @return true if enabled, false if disabled
     */
    protected boolean isInstanceEnabled() {
        return getState(false).enabled;
    }

    /**
     * Enable or disable instance
     * @param enabled true to enable, false to disable
     */
    protected void setInstanceEnabled(boolean enabled) {
        getState().enabled = enabled;
    }

    /**
     * Temporary disable on client side for given amount of milliseconds. Remember that time spent sending command to
     * client side is not included in given time.
     * @param millisec Milliseconds client side will disable beforeunload
     */
    protected void temporaryDisableInstance(long millisec) {
        if(millisec < 0L) {
            throw new IllegalArgumentException("Invalid amount of milliseconds given " + millisec);
        }
        getRpcProxy(BeforeUnloadClientRpc.class).disableTemporary(millisec);
    }

    /**
     * Set BeforeUnload enabled or disabled
     * @param ui UI where BeforeUnload should be enabled or disabled
     * @param enabled true to enable, false to disable
     */
    public static void setEnabled(UI ui, boolean enabled) {
        get(Objects.requireNonNull(ui)).setInstanceEnabled(enabled);
    }

    /**
     * Set BeforeUnload enabled or disabled in current UI context
     * @param enabled true to enable, false to disable
     * @throws IllegalStateException If UI can not be resolved from context
     */
    public static void setEnabled(boolean enabled) throws IllegalStateException {
        get().setInstanceEnabled(enabled);
    }

    /**
     * Enable before unloading verification on current UI
     * @throws IllegalStateException If UI can not be resolved from context
     */
    public static void enable() throws IllegalStateException {
        setEnabled(true);
    }

    /**
     * Disable before unloading verification on current UI
     * @throws IllegalStateException If UI can not be resolved from context
     */
    public static void disable() throws IllegalStateException {
        setEnabled(false);
    }

    /**
     * Enable at given UI
     * @param ui UI where verification will be asked when leaving
     */
    public static void enable(UI ui) {
        setEnabled(ui,true);
    }

    /**
     * Disable at given UI
     * @param ui UI where verification will not be asked when leaving
     */
    public static void disable(UI ui) {
        optionalGet(ui).map(beforeUnload -> beforeUnload.isInstanceEnabled()).orElse(false);
        setEnabled(ui, false);
    }

    /**
     * Check if BeforeUnload is enabled of current UI. If current UI can not be resolved will always return false.
     * @return true if enabled, false if not
     */
    public static boolean isEnabled() {
        return optionalGet().map(beforeUnload -> beforeUnload.isInstanceEnabled()).orElse(false);
    }

    /**
     * Check if BeforeUnload is enabled of current UI
     * @return true if enabled, false if not
     */
    public static boolean isEnabled(UI ui) {
        return optionalGet(Objects.requireNonNull(ui))
                .map(beforeUnload -> beforeUnload.isInstanceEnabled())
                .orElse(false);
    }

    /**
     * Temporary disable on client side for given time. Might be useful when client side might cause navigation event.
     * @param ui UI with before unload
     * @param millisecs How many milliseconds the client side should be temporary disabled
     */
    public static void temporaryDisable(UI ui, long millisecs) {
        optionalGet(ui).ifPresent(bu -> bu.temporaryDisableInstance(millisecs));
    }

    /**
     * Temporary disable on client side for given time. Might be useful when client side might cause navigation event.
     * @param millisecs How many milliseconds the client side should be temporary disabled
     */
    public static void temporaryDisable(long millisecs) {
        optionalGet().ifPresent(bu -> bu.temporaryDisableInstance(millisecs));
    }

    @Override
    protected BeforeUnloadState getState() {
        return (BeforeUnloadState)super.getState();
    }

    @Override
    protected BeforeUnloadState getState(boolean markDirty) {
        return (BeforeUnloadState)super.getState(markDirty);
    }
}
