package com.plannerapp.init;

import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.enums.PriorityName;
import com.plannerapp.repo.PriorityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InitPriority implements CommandLineRunner {

    private final Map<PriorityName, String> priorityDescriptions = Map.of(
            PriorityName.URGENT, "An urgent problem that blocks the system use until the issue is resolved.",
            PriorityName.IMPORTANT, "A core functionality that your product is explicitly supposed to perform is compromised.",
            PriorityName.LOW, "Should be fixed if time permits but can be postponed."
    );


    private final PriorityRepository priorityRepository;


    public InitPriority(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        long count = priorityRepository.count();

        if (count > 0) {
            return;
        }

        if (count == 0) {
            for (PriorityName priorityName : priorityDescriptions.keySet()) {
                Priority priority = new Priority(priorityName, priorityDescriptions.get(priorityName));

                priorityRepository.save(priority);
            }
        }
    }


}
