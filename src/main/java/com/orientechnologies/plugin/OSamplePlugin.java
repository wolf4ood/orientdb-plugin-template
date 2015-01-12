package com.orientechnologies.plugin;

import com.orientechnologies.orient.core.exception.OConfigurationException;
import com.orientechnologies.orient.server.OServer;
import com.orientechnologies.orient.server.config.OServerParameterConfiguration;
import com.orientechnologies.orient.server.network.OServerNetworkListener;
import com.orientechnologies.orient.server.network.protocol.http.ONetworkProtocolHttpAbstract;
import com.orientechnologies.orient.server.plugin.OServerPluginAbstract;
import com.orientechnologies.plugin.command.OSamplePostCommand;

/**
 * Created by Enrico Risa on 12/01/15.
 */
public class OSamplePlugin extends OServerPluginAbstract {


    private OServer server;

    @Override
    public String getName() {
        return "Sample Plugin";
    }

    @Override
    public void config(OServer oServer, OServerParameterConfiguration[] iParams) {
        super.config(oServer, iParams);
        this.server = oServer;
    }

    @Override
    public void startup() {
        super.startup();
        registerCommands();
    }

    private void registerCommands() {
        final OServerNetworkListener listener = server.getListenerByProtocol(ONetworkProtocolHttpAbstract.class);
        if (listener == null)
            throw new OConfigurationException("HTTP listener not found");

        listener.registerStatelessCommand(new OSamplePostCommand());

    }

    @Override
    public void shutdown() {
        super.shutdown();
        unregisterCommands();

    }

    private void unregisterCommands() {

        final OServerNetworkListener listener = server.getListenerByProtocol(ONetworkProtocolHttpAbstract.class);
        if (listener == null)
            throw new OConfigurationException("HTTP listener not found");

        listener.unregisterStatelessCommand(OSamplePostCommand.class);

    }
}
