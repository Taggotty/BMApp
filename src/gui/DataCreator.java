package gui;

import core.Series;

public interface DataCreator {

    void onCreateBook();
    void onCreateBook(Series s);
    void onCreateSingleBook();
    void onCreateSeries();
}
