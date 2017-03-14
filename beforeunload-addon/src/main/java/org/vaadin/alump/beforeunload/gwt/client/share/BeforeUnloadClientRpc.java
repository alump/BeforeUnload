package org.vaadin.alump.beforeunload.gwt.client.share;

import com.vaadin.shared.communication.ClientRpc;

/**
 * Created by alump on 13/03/2017.
 */
public interface BeforeUnloadClientRpc extends ClientRpc {

    void disableTemporary(long milliSecs);

}
