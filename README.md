# WebCrawlerSample
This programs crawl a list of web sites from a file.

## Installation
1. Just download or clone this source code. 
1. In your download folder (or local repository), type in your console **cd  web.crawler.shell**.
1. Then you will need to build this code with the command **mvn clean install**.
1. After that, you must type **java -jar target\web.crawler.shell-1.0-SNAPSHOT.jar**.
1. Finally, you will see Spring Boot init screen.

## Usage
After the installation, you will see on your console spring-shell prompt (**shell:>**).
Here you can type:
* **shell:>crawl-web-sites -i _implementation_ -op _origin path_ -dp _destination path_ -p _pattern_**.
The parameters are:
1. **implementation**: flags **-i** or **--impl**. 
    - This parameter defines the implementation type for the crawling. 
    - This parameter is **_Optional_**.
    - Posible values: 
         - **default**: Crawls the websites sequentially within a **_for_** statement. This is the value **_default_** for this parameter.
         - **threadExecutor**: Crawls the websites using **_Thread Executor API_**.
         - **streams**: Crawls the websites using **_Java 8 Stream API_**.
2. **origin path**: flags **-op** or **--origin-path**.
    - This parameter defines the source path of the file that contains the URL of the website.
    - This parameter is **_Mandatory_**.
3. **destination path**: flags **-dp** or **--destination-path**.
    - This parameter defines the destination path of the file will contain the crawling results.
    - This parameter is **_Mandatory_**.
4. **pattern**: flags **-p** or **--pattern**. 
    - This parameter defines the pattern to be searched. 
    - This parameter is **_Optional_**.
    - Posible values: 
         - **asterisk(*)**: Wildcard that allows you to use all compatible patterns in the search.
         - **twitter-links**: This patterns allows you to search for **_Twitter Links_**.
         - **twitter-accounts**: This patterns allows you to search for **_Twitter Accounts_**. 
         - **twitter-hashtags**: This patterns allows you to search for **_Twitter Hashtags_**. 
         - **name-locations**: This patterns allows you to search for **_Locations Proper Names_**. 
         - **name-persons**: This patterns allows you to search for **_Peoples Proper Names_**. 
         
## Examples
* crawl-web-sites -i "streams" -op "C:\\Workspace\\site-list.txt" -dp "C:\\Workspace\\out"
* crawl-web-sites -i "threadExecutor" -op "C:\\Workspace\\site-list.txt" -dp "C:\\Workspace\\out"
* crawl-web-sites -op "C:\\Workspace\\site-list.txt" -dp "C:\\Workspace\\out"
* crawl-web-sites -op "C:\\Workspace\\site-list.txt" -dp "C:\\Workspace\\out" -p twitter-links
