package com.di4.bootcamp.subscriptions.services.imp;

import com.di4.bootcamp.subscriptions.dto.SubscriptionDto;
import com.di4.bootcamp.subscriptions.entities.Subscription;
import com.di4.bootcamp.subscriptions.exceptions.InternalServerErrorException;
import com.di4.bootcamp.subscriptions.repositories.SubscriptionRepository;
import com.di4.bootcamp.subscriptions.services.SubscriptionService;
import com.di4.bootcamp.subscriptions.utils.constants.ExceptionConstants;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImp implements SubscriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionServiceImp.class);

    private final SubscriptionRepository subscriptionRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public SubscriptionServiceImp(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public SubscriptionDto createSubscription(SubscriptionDto subscriptionDto)
            throws InternalServerErrorException {

        Subscription subscription = new Subscription();
        subscription.setType(subscriptionDto.getType());
        subscription.setPrice(subscriptionDto.getPrice());
        subscription.setStartDate(subscriptionDto.getStartDate());
        subscription.setEndDate(subscriptionDto.getEndDate());

        try {
            subscriptionRepository.save(subscription);
        } catch (final Exception exception) {
            LOGGER.error(ExceptionConstants.INTERNAL_SERVER_ERROR, exception);
            throw new InternalServerErrorException(ExceptionConstants.INTERNAL_SERVER_ERROR);
        }

        return modelMapper.map(subscription, SubscriptionDto.class);
    }

    @Override
    public List<SubscriptionDto> getAllSubscriptions() {

        return subscriptionRepository.findAll().stream()
                                     .map(subscription -> modelMapper.map(subscription,
                                             SubscriptionDto.class)).collect(Collectors.toList());
    }
}
