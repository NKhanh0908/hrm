package com.project.hrm.services.impl;

import com.project.hrm.dto.applyDTO.JobOfferDetailsDTO;
import com.project.hrm.dto.othersDTO.InfoApply;
import com.project.hrm.dto.othersDTO.InterviewLetter;
import com.project.hrm.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {


    private final JavaMailSender javaMailSender;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Sends an interview invitation email to the applicant.
     *
     * @param infoApply       information of the applicant
     * @param interviewLetter the interview letter details including time and location
     */
    @Async("emailInterviewTaskExecutor")
    @Override
    public void notificationInterview(InfoApply infoApply, InterviewLetter interviewLetter) {
        log.info("Sending interview to email...: {}", infoApply.getEmail());

        String emailTo = infoApply.getEmail();
        String subject = "Interview invitation letter";
        String content = getContentNotificationForInterview(infoApply, interviewLetter);

        sendMail(emailTo, subject, content);
    }

    /**
     * Sends a job offer email to the selected candidate after being hired.
     *
     * @param infoApply           information of the applicant
     * @param jobOfferDetailsDTO  the details of the job offer such as department, start date, etc.
     */
    @Async("emailHiredTaskExecutor")
    @Override
    public void notificationForHired(InfoApply infoApply, JobOfferDetailsDTO jobOfferDetailsDTO) {
        log.info("Sending hired to email...: {}", infoApply.getEmail());

        String emailTo = infoApply.getEmail();
        String subject = "Interview invitation letter";
        String content = getContentNotificationForJobOffer(infoApply, jobOfferDetailsDTO);

        sendMail(emailTo, subject, content);
    }

    /**
     * Sends a rejection email to the applicant.
     *
     * @param infoApply information of the applicant
     */
    @Async("emailRejectTaskExecutor")
    @Override
    public void notificationForRejection(InfoApply infoApply) {
        log.info("Sending reject to email...: {}", infoApply.getEmail());

        String emailTo = infoApply.getEmail();
        String subject = "Interview invitation letter";
        String content = getContentNotificationForRejection(infoApply);

        sendMail(emailTo, subject, content);
    }

    /**
     * Generates the HTML content for an interview invitation email.
     *
     * @param infoApply       applicant info
     * @param interviewLetter interview schedule and location
     * @return HTML content for the interview invitation
     */
    private String getContentNotificationForInterview(InfoApply infoApply, InterviewLetter interviewLetter) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a, dd MMMM yyyy", Locale.ENGLISH);
        String formattedTime = interviewLetter.getInterviewTime().format(formatter);

        return """
        <html>
        <body style="font-family: Arial, sans-serif; line-height: 1.6;">
            <h2 style="color: #2c3e50;">Interview Invitation</h2>
            <p>Dear <strong>%s</strong>,</p>

            <p>Thank you for your interest in the <strong>%s</strong> position at our company.</p>

            <p>We are pleased to inform you that your application has been shortlisted. We would like to invite you to attend an interview as per the following details:</p>

            <ul>
                <li><strong>Date & Time:</strong> %s</li>
                <li><strong>Location:</strong> %s</li>
            </ul>

            <p>Please bring your resume and any other relevant documents to the interview. If you have any questions or need to reschedule, feel free to contact us in advance.</p>

            <p>We look forward to meeting you and learning more about your qualifications.</p>

            <p>Best regards,<br/>
            Recruitment Team<br/>
            SGU Enterprise Information System</p>
        </body>
        </html>
        """.formatted(
                infoApply.getName(),
                infoApply.getPositionApply(),
                formattedTime,
                interviewLetter.getAddress()
        );
    }

    /**
     * Generates the HTML content for a job offer email after being hired.
     *
     * @param infoApply          applicant info
     * @param details job offer details
     * @return HTML content for the job offer
     */
    private String getContentNotificationForJobOffer(InfoApply infoApply, JobOfferDetailsDTO details) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a, dd MMMM yyyy", Locale.ENGLISH);
        String formattedTime = details.getReportDateTime().format(formatter);

        return """
        <html>
        <body style="font-family: Arial, sans-serif; line-height: 1.6;">
            <h2 style="color: #2c3e50;">Official Job Offer</h2>
           \s
            <p>Dear <strong>%s</strong>,</p>
           \s
            <p>We are delighted to inform you that you have been selected for the <strong>%s</strong> position at our organization.</p>
           \s
            <p>We were truly impressed with your background and performance during the interview process, and we believe you will be a valuable addition to our team.</p>
           \s
            <p>Please be present to complete the onboarding process and receive your job assignment at the following time and location:</p>
           \s
            <ul>
                <li><strong>Date & Time:</strong> %s</li>
                <li><strong>Location:</strong> %s</li>
            </ul>
           \s
            <p>If you have any questions or are unable to attend at the specified time, please reach out to our HR representative below:</p>
           \s
            <ul>
                <li><strong>Contact Person:</strong> %s</li>
                <li><strong>Email:</strong> <a href="mailto:%s">%s</a></li>
                <li><strong>Phone:</strong> %s</li>
            </ul>
           \s
            <p>We look forward to welcoming you to our team and starting a successful journey together.</p>
           \s
            <p>Warm regards,<br/>
            Recruitment Team<br/>
            SGU Enterprise Information System</p>
        </body>
        </html>
   \s""".formatted(
                infoApply.getName(),
                infoApply.getPositionApply(),
                formattedTime,
                details.getReportLocation(),
                details.getContactPersonName(),
                details.getContactEmail(),
                details.getContactEmail(),
                details.getContactPhone()
        );
    }

    /**
     * Generates the HTML content for a rejection email.
     *
     * @param infoApply applicant info
     * @return HTML content for rejection notification
     */
    private String getContentNotificationForRejection(InfoApply infoApply) {
        return """
        <html>
        <body style="font-family: Arial, sans-serif; line-height: 1.6;">
            <h2 style="color: #2c3e50;">Application Status Update</h2>
           \s
            <p>Dear <strong>%s</strong>,</p>
           \s
            <p>Thank you for your interest in the <strong>%s</strong> position and for the time you invested in our recruitment process.</p>
           \s
            <p>After careful review, we regret to inform you that we have chosen to move forward with other candidates whose skills and experience more closely match our current needs.</p>
           \s
            <p>This was not an easy decision—your qualifications are impressive, and we truly appreciate the insights you brought to the interview.</p>
           \s
            <p>Although we are unable to offer you a role at this time, we encourage you to apply again in the future should a position arise that aligns with your background.</p>
           \s
            <p>If you would like feedback or have any questions, please feel free to reply to this email, and we will be happy to assist.</p>
           \s
            <p>We wish you every success in your career.</p>
           \s
            <p>Warm regards,<br/>
            Recruitment Team<br/>
            SGU Enterprise Information System</p>
        </body>
        </html>
   \s""".formatted(
                infoApply.getName(),
                infoApply.getPositionApply()
        );
    }

    /**
     * Sends an HTML email using the provided recipient, subject, and HTML content.
     *
     * @param toEmail     recipient email address
     * @param subject     email subject
     * @param htmlContent email body in HTML format
     */
    private void sendMail(final String toEmail, final String subject, final String htmlContent) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setFrom("khanhnq0908@gmail.com");
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
            log.info("Email successfully sent to {}", toEmail);

        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", toEmail, e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error while sending email: {}", e.getMessage(), e);
        }
    }

}
