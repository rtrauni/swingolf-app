package at.swingolf.app.web;

import rx.Observer;

public class RecordObserver implements Observer<String[]> {
    class PlayerEntry {

    }

    @Override
    public void onCompleted() {
        System.out.println("Finished.");
    }

    @Override
    public void onError(Throwable exception) {
        System.out.println("Oops!");
        exception.printStackTrace();
    }

    @Override
    public void onNext(String[] r) {
        System.out.println(r);
    }

}