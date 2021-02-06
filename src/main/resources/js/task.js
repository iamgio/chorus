/**
 * Runs an action later on JavaFX thread
 * @param action action to be ran
 */
function runLater(action) {
    javafx.application.Platform.runLater(action);
}

/**
 * UNSUPPORTED SINCE GRAALVM MIGRATION. Runs an action on another thread
 * @param action action to be ran
 */
function runAsync(action) {
    print('[' + getThisAddon().getName() + '] Multithreading is currently unsupported.');
    //new java.lang.Thread(action).start();
}

/**
 * Executes a task every X time for a certain amount of time
 * @param action task to be run
 * @param millis time in milliseconds to wait between tasks
 * @param times amount of cycles to be executed
 */
function cycle(action, millis, times) {
    chorus_type('util.Utils').wait(action, millis, times);
}

/**
 * Executes a task for an indefinite amount of time
 * @param action task to be run
 * @param millis time in milliseconds to wait between tasks
 */
function setInterval(action, millis) {
    cycle(action, millis, -1);
}

/**
 * Waits and executes a task
 * @param action task to be run
 * @param millis time in milliseconds to wait
 */
function wait(action, millis) {
    cycle(action, millis, 1);
}

/**
 * Waits and executes a task
 * @param action task to be run
 * @param millis time in milliseconds to wait
 * @see wait
 */
function setTimeout(action, millis) {
    wait(action, millis);
}

/**
 * Creates a listener for a JavaFX property
 * @param property target property
 * @param action task to be run when the value changes
 */
function listen(property, action) {
    chorus_type('util.Utils').listen(property, action);
}