package com.furious.vehicle.inspect.domain.exceptions

import com.furious.vehicle.inspect.domain.validation.handler.Notification

class NotificationException(aMessage: String, notification: Notification) :
    DomainException(aMessage, notification.getErrors())