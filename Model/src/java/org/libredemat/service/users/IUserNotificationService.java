package org.libredemat.service.users;

import org.libredemat.business.users.Adult;
import org.libredemat.business.users.MeansOfContactEnum;
import org.libredemat.exception.CvqException;
import org.libredemat.security.annotation.IsUser;
import org.libredemat.service.users.job.UserNotificationJob.NotificationType;

public interface IUserNotificationService {

    void contact(@IsUser Adult adult, MeansOfContactEnum moc, String to, String message, String note)
        throws CvqException;

    void notifyByEmail(String from, String recipient, String subject, String body, byte[] data, String attachmentName)
        throws CvqException;

    void notifyBySms(String to, String body)
        throws CvqException;

    void sendNotification(Adult adult, NotificationType type, String note);
}
