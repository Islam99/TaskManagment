package banquemisr.challenge05.TaskManagement.Mailer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class EmailConfig {

    @Value("${spring.mail.port}")
    private int port ;
    @Value("${spring.mail.host}")
    private String host ;
    @Value("${spring.mail.username}")
    private String user ;
    @Value("${spring.mail.password}")
    private String password ;
    @Value("${spring.mail.properties.mail.transport.protocol}")
    private String protocol ;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String authEnable ;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String stattllsEnable ;
    @Value("${spring.mail.properties.mail.debug}")
    private String debug ;
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(user);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", authEnable);
        props.put("mail.smtp.starttls.enable", stattllsEnable);
        props.put("mail.debug", debug);
        return mailSender;
    }
}
