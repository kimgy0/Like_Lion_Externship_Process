package com.shop.projectlion.global.security.metadatasource;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class UrlFilterInvocationSecurityMetaDataSource implements FilterInvocationSecurityMetadataSource {
    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap = new LinkedHashMap<>();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o)
            throws IllegalArgumentException {


        HttpServletRequest request = ((FilterInvocation) o).getRequest();
        requestMap.put(new AntPathRequestMatcher("/admin/**"), Arrays.asList(new SecurityConfig("ROLE_ADMIN")));

        if(requestMap != null){
            Set<Map.Entry<RequestMatcher, List<ConfigAttribute>>> entry = requestMap.entrySet();

            for (Map.Entry<RequestMatcher, List<ConfigAttribute>> requestMatcherListEntry : entry) {
                RequestMatcher matcher = requestMatcherListEntry.getKey();
                if(matcher.matches(request)){
                    return requestMatcherListEntry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet();
        Iterator var2 = this.requestMap.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry = (Map.Entry)var2.next();
            allAttributes.addAll((Collection)entry.getValue());
        }

        return allAttributes;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
