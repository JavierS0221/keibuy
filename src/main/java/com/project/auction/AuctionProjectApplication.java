package com.project.auction;

import com.project.auction.controller.auctions.AuctionsWSController;
import com.project.auction.model.Item;
import com.project.auction.service.ItemService;
import com.project.auction.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:config.properties")
@PropertySource("classpath:mail.properties")
@PropertySource("classpath:mysql_config.properties")
public class AuctionProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionProjectApplication.class, args);

    }


    public interface TaskMBean {
        void setEnabled(boolean enabled);
    }

    @Component
    public static class Task implements TaskMBean {
        private static final Logger log = LoggerFactory.getLogger(Task.class);
        private boolean enabled = true;

        ItemService itemService;
        AuctionsWSController auctionsWSController;

        @Autowired
        public Task(ItemService itemService, AuctionsWSController auctionsWSController) {
            this.itemService = itemService;
            this.auctionsWSController = auctionsWSController;
        }

        @PostConstruct
        private void init() throws Exception {
            final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            mBeanServer.registerMBean(this, new ObjectName(this.getClass().getSimpleName(), "name", "control"));
        }

        @Scheduled(fixedDelay = 1000L)
        public void run() {
            if (enabled) {
                try {
                    List<Item> listItems = itemService.listItems();
                    for (Item item : listItems) {
                        if (item.isFinalized()) continue;

                        long distance = item.getFinishDate().getTime() - new Date().getTime();
                        if (distance < 0) {
                            itemService.setFinalized(item, true);
                            this.auctionsWSController.finalize(item);
                            itemService.sendEmails(item);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        @Override
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }


}
