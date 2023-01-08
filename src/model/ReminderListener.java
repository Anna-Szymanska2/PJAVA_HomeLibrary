package model;


/**
 * ReminderListener is an interface for receiving information that reminder should be displayed.
 */
public interface ReminderListener {
    void reminderSendAction(Reminder reminder);
}
