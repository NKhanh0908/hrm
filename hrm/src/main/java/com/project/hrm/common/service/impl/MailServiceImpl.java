package com.project.hrm.common.service.impl;

import com.project.hrm.recruitment.dto.applyDTO.JobOfferDetailsDTO;
import com.project.hrm.recruitment.dto.othersDTO.InfoApply;
import com.project.hrm.recruitment.dto.othersDTO.InterviewLetter;
import com.project.hrm.common.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
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
     *  Sends an OTP email for verification purposes.
     *
     * @param email recipient's email address
     * @param otp one-time password to be sent
     * @param expiryMinutes the number of minutes until the OTP expires
     */
    @Async("emailOtpTaskExecutor")
    @Override
    public void sendOtpEmail(String email, String otp, int expiryMinutes) {
        log.info("Sending OTP email to: {}", email);

        String subject = "Password Reset OTP - HRM Enterprise";
        String content = getOtpEmailContent(email, otp, expiryMinutes);

        sendMail(email, subject, content);
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
     * Generates HTML content for OTP email
     * @param email recipient email
     * @param otp the OTP code
     * @param expiryMinutes OTP expiry time in minutes
     * @return HTML content for OTP email
     */
    private String getOtpEmailContent(String email, String otp, int expiryMinutes) {
        return """
    <html>
    <body style="font-family: Arial, sans-serif; line-height: 1.6; max-width: 600px; margin: 0 auto; padding: 20px;">
        <div style="background-color: #f8f9fa; padding: 20px; border-radius: 8px; margin-bottom: 20px;">
            <h2 style="color: #2c3e50; margin-bottom: 20px;">Password Reset Request</h2>
            
            <p>Dear User,</p>
            
            <p>We received a request to reset your password for your SGU Enterprise account associated with <strong>%s</strong>.</p>
            
            <div style="background-color: #ffffff; padding: 20px; border-radius: 8px; border-left: 4px solid #3498db; margin: 20px 0;">
                <h3 style="color: #2c3e50; margin-bottom: 10px;">Your OTP Code:</h3>
                <div style="font-size: 32px; font-weight: bold; color: #3498db; letter-spacing: 5px; text-align: center; padding: 15px; background-color: #f1f3f4; border-radius: 4px;">
                    %s
                </div>
            </div>
            
            <div style="background-color: #fff3cd; padding: 15px; border-radius: 4px; border-left: 4px solid #ffc107; margin: 20px 0;">
                <p style="margin: 0; color: #856404;"><strong>Important:</strong></p>
                <ul style="margin: 10px 0; color: #856404;">
                    <li>This OTP will expire in <strong>%d minutes</strong></li>
                    <li>You have a maximum of <strong>5 attempts</strong> to enter the correct OTP</li>
                    <li>Do not share this OTP with anyone</li>
                </ul>
            </div>
            
            <p>If you did not request this password reset, please ignore this email. Your password will remain unchanged.</p>
            
            <p>For security reasons, please do not reply to this email. If you need assistance, please contact our support team.</p>
            
            <hr style="border: none; border-top: 1px solid #eee; margin: 30px 0;">
            
            <p style="color: #7f8c8d; font-size: 14px;">
                Best regards,<br/>
                IT Support Team<br/>
                SGU Enterprise Information System
            </p>
        </div>
    </body>
    </html>
    """.formatted(email, otp, expiryMinutes);
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
