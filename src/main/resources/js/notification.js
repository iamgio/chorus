/**
 * Sends a notification
 * @param text notification text
 * @param isError whether the notification referred to an error
 */
function sendNotification(text, isError) {
    var Notification = chorus_type('notification.Notification');
    var NotificationType = chorus_type('notification.NotificationType');
    new Notification(text, isError ? NotificationType.ERROR : NotificationType.MESSAGE).send();
}

/**
 * Displays a notification
 * @param text notification text
 * @see sendNotification
 */
function alert(text) {
    sendNotification(text, false);
}

/**
 * Displays an error
 * @param text error text
 * @see sendNotification
 */
function error(text) {
    sendNotification(text, true);
}