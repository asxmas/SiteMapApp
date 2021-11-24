# SiteMapApp

The Application will create the map of the site specified by the user.

To run the Application, user must enter some value:

    - URL of site. Starting from this URL, the site will be crawled. Also, this site will be a kind of pattern with which other sites will be compared to exclude third-party links from the sitemap;
    - Name of file. The created sitemap will be written to a file with this name. If the name is not specified, the file will have the name of the site (without specifying the protocol (http:// or https://);
    - Time of threads sleep. Many sites are protected from DDoS attacks. In case of too frequent access to the site from one IP, many sites block incoming requests. To do this, it is necessary to maintain pauses between requests for the pages of the site. The default pause value is 100 milliseconds. To leave the value unchanged, do not enter anything and immediately press "Enter".

In class Properties you may change next values:

    - USER_AGENT and REFERRER. Many sites are protected from incoming bots. To bypass this protection, the User Agent is used. Thus, the program "pretends" to be a bang;
    - THREAD_SLEEP. It is default value of time to pause between requests to site.