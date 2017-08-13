import pandas as pd
import pull
import store


class Main:
    def __call__(self, tickers, start_date, end_date, pull_latest=False):
        """
        __call__ is the main method for the Main class
        """
        pull_main = pull.Pull()
        dbs = store.DBStore()
        if pull_latest:
            dbs(pull_main(
                source='Google',
                tickers=tickers,
                start_date=start_date,
                end_date=end_date
            ))
        r = pull_main(
            source='Database',
            tickers=tickers,
            start_date=start_date,
            end_date=end_date
        )
        print(r)
        
        
if __name__ == '__main__':
    TICKERS1 = ['CHOC', 'SJNK', 'HYGH', 'EMF', 'IYR', 'SPY']
    main_class = Main()
    main_class(tickers=TICKERS1, start_date='2017-07-01', end_date='2017-07-31', pull_latest=True)
    main_class(tickers=['AAPL', 'GOOG'], start_date='2017-07-01', end_date='2017-07-31', pull_latest=True)
