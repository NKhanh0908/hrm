package com.project.hrm.services.impl;

import com.project.hrm.dto.othersDTO.InfoApply;
import com.project.hrm.dto.othersDTO.InterviewLetter;
import com.project.hrm.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;


    @Override
    public void notificationInterview(InfoApply infoApply, InterviewLetter interviewLetter) {
        String emailTo = infoApply.getEmail();
        String subject = "Interview invitation letter";
        String content = getContentNotificationForInterview(infoApply, interviewLetter);

        sendMail(emailTo, subject, content);
    }

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


    private void sendMail(final String recipientEmail, final String subject, final String content){
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(content, true); // true indicates HTML

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
