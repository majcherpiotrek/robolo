package com.ksm.robolo.roboloapp.scheduledtasks;

import com.ksm.robolo.roboloapp.domain.UserEntity;
import com.ksm.robolo.roboloapp.domain.VerificationToken;
import com.ksm.robolo.roboloapp.repository.UserRepository;
import com.ksm.robolo.roboloapp.repository.VerificationTokenRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
public class DeleteUnverifiedUserAccountsScheduledTask {

    private static final Logger logger = Logger.getLogger(DeleteUnverifiedUserAccountsScheduledTask.class);

    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public DeleteUnverifiedUserAccountsScheduledTask(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Scheduled(fixedRate = 1000 * 60) // Every minute
    @Transactional
    public void deleteUnverifiedUserAccounts() {
        logger.info("Searching for unverified users ...");
        List<VerificationToken> verificationTokenList = verificationTokenRepository.findAll();
        if (verificationTokenList != null && !verificationTokenList.isEmpty()) {
            Date now = new Date();
            for (VerificationToken token : verificationTokenList) {
                if (now.after(token.getExpiryDate())) {
                    UserEntity user = token.getUser();
                    if (!user.isEnabled()) {
                        logger.info("Deleting unverified user: " + user.getUsername());
                        verificationTokenRepository.delete(token);
                        userRepository.delete(user);
                    }
                }
            }
        }
    }
}
