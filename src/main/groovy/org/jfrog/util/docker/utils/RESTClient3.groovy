package org.jfrog.util.docker.utils

import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import groovyx.net.http.ResponseParseException
import org.codehaus.groovy.runtime.IOGroovyMethods

class RESTClient3 extends RESTClient {
    RESTClient3() { super() }

    RESTClient3(Object defaultURI ) throws URISyntaxException {
        super( defaultURI )
    }

    @Override
    protected HttpResponseDecorator defaultSuccessHandler( HttpResponseDecorator resp, Object data )
            throws ResponseParseException {
        try
        {
            if (data instanceof InputStream)
            {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream()
                IOGroovyMethods.leftShift(buffer, (InputStream) data)
                resp.setData(new ByteArrayInputStream(buffer.toByteArray()))
                return resp
            }
            if (data instanceof Reader)
            {
                StringWriter buffer = new StringWriter()
                IOGroovyMethods.leftShift(buffer, (Reader) data)
                resp.setData(new StringReader(buffer.toString()))
                return resp
            }
            return super.defaultSuccessHandler(resp, data)
        }
        catch (IOException ex)
        {
            throw new ResponseParseException(resp, ex)
        }
    }
}