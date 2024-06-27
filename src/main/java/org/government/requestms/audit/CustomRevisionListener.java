package org.government.requestms.audit;

import org.government.requestms.entity.CustomRevisionEntity;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomRevisionListener implements RevisionListener {
    @Override
    public void newRevision(Object revisionEntity) {
        CustomRevisionEntity customRevisionEntity = (CustomRevisionEntity) revisionEntity;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            customRevisionEntity.setEmail(authentication.getName());
        } else {
            customRevisionEntity.setEmail("Anonymous");
        }
    }
}
