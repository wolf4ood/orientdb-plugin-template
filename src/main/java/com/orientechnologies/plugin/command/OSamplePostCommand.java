package com.orientechnologies.plugin.command;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.server.network.protocol.http.OHttpRequest;
import com.orientechnologies.orient.server.network.protocol.http.OHttpResponse;
import com.orientechnologies.orient.server.network.protocol.http.command.OServerCommandAuthenticatedDbAbstract;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enrico Risa on 12/01/15.
 */
public class OSamplePostCommand extends OServerCommandAuthenticatedDbAbstract {

    private static final String[] NAMES = {"POST|sampleEndPoint/*"};

    @Override
    public boolean execute(OHttpRequest iRequest, OHttpResponse iResponse) throws Exception {
        final String[] parts = checkSyntax(iRequest.url, 2, "Syntax error: sampleEndPoint");

        String body = iRequest.content;

        ODocument content = new ODocument().fromJSON(body, "noMap");
        ODatabaseDocumentTx db = getProfiledDatabaseInstance(iRequest);
        OrientGraph graph = new OrientGraph(db);
        String clazz = content.field("clazz");
        String field = content.field("field");
        Object value = content.field("value");
        Iterable<Vertex> vertices = graph.getVertices(clazz + "." + field, value);

        List<ODocument> results = new ArrayList<ODocument>();
        for (Vertex vertice : vertices) {
            OrientVertex v = (OrientVertex) vertice;
            results.add(v.getRecord());
        }
        iResponse.writeResult(results);
        return false;
    }

    @Override
    public String[] getNames() {
        return NAMES;
    }
}
