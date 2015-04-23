/*
 * Copyright 2015 Alexander Klimuk
 * aklimuk@gmail.com
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
package org.aklimuk.cas.rest.authenti;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;

/**
 * @author Alexander Klimuk
 *
 */
public class CasRestAuthenticationFilter extends AbstractCasFilter {

    private String casRestLoginUrl;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        final HttpSession session = req.getSession(false);

        // CAS ticket present and already validated?
        Assertion assertion = getAssertion(session);
        if (assertion != null) {
            chain.doFilter(request, response);
            return;
        }

        String serviceTicket = req.getHeader("CAS-Service-Ticket");

        // Client sent us a ticket.
        // It will be validated by another filter.
        if (StringUtils.isNotBlank(serviceTicket)) {
            chain.doFilter(req, resp);
            return;
        }

        // Tell the client where it can obtain a ticket.
        resp.setStatus(401);
        resp.setHeader("Location", casRestLoginUrl);
    }

    private Assertion getAssertion(HttpSession session) {
        if (session != null) {
            return (Assertion) session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        } else {
            return null;
        }
    }

    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
        if (!isIgnoreInitConfiguration()) {
            super.initInternal(filterConfig);
            casRestLoginUrl = filterConfig.getInitParameter("casRestLoginUrl");
        }
    }

}
