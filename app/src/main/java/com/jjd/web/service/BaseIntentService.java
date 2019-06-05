package com.jjd.web.service;


/**
 * @author yuansui
 */
abstract public class BaseIntentService extends IntentServiceEx {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BaseIntentService(String name) {
        super(name);
    }


}
