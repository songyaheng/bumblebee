**Bumblebee-spider**

该项目能够很轻松的创建自己的爬虫应用，并原生支持各种请求配置，头信息设置，并支持但任务，多任务爬虫

首先针对普通网页爬虫直接如下代码即可

单页面单任务：

    public class AppStoreSpider extends AbstractSpider<HttpGet,String> {
    
        public AppStoreSpider() {
            super("utf-8");
        }
    
        @Override
        public ClientConfig getClientConfig() {
            return null;
        }
    
        @Override
        public HttpGet getRequest() {
            HttpGet httpGet = new HttpGet("https://itunes.apple.com/WebObjects/MZStore.woa/wa/userReviewsRow?cc=cn&id=923920872&displayable-kind=11&startIndex=0&endIndex=100&sort=0&appVersion=all");
            httpGet.setHeader("User-Agent", "iTunes/11.0 (Windows; Microsoft Windows 7 Business Edition Service Pack 1 (Build 7601)) AppleWebKit/536.27.1");
            return httpGet;
        }
    
        @Override
        public Processor<PageHtml<HttpGet>, String> getProcessor() {
            return new HtmlProcessor<HttpGet, String>() {
                @Override
                public String process(PageHtml<HttpGet> html) {
                    System.out.println(html.getHtmlOrJson());
                    return null;
                }
            };
        }
    
    
        public static void main(String[] args) {
            AppStoreSpider appStoreSpider = new AppStoreSpider();
            appStoreSpider.run();
        }
    }

多页面多任务

    public class MutiTaskSpider extends AbstractSpiderExecutorService<HttpGet, String>{

        public MutiTaskSpider() {
            super(3, 1000);
        }
    
        private static final String baseUrl = "http://miassist.app.xiaomi.com/v3/getComments?appId=74172&ps=1&page=";
    
        private AtomicInteger atomicInteger = new AtomicInteger(1);
    
    
        @Override
        public Processor<PageHtml<HttpGet>, String> getProcessor() {
            return new JsonProcesser<HttpGet, String>() {
                @Override
                public String process(PageHtml<HttpGet> json) {
                    int page = atomicInteger.incrementAndGet();
                    System.out.println(Thread.currentThread().getName());
                    String js = json.getHtmlOrJson();
                    JSONObject jo = JSON.parseObject(js);
                    JSONArray ja = jo.getJSONArray("list");
                    ja.forEach(o -> {
                        String content = ((JSONObject) o).getString("comment");
                        String r = StringEscapeUtils.unescapeHtml4(content);
                        System.out.println(r);
                    });
                    put(baseUrl + page, HttpGet.class);
                    return null;
                }
            };
        }
    
        @Override
        public ClientConfig getClientConfig() {
            return null;
        }
    
        public static void main(String[] args) {
            MutiTaskSpider mutiTaskSpider = new MutiTaskSpider();
            mutiTaskSpider.run();  //多线程
            //mutiTaskSpider.runAsyn(); //并行
            mutiTaskSpider.put("http://miassist.app.xiaomi.com/v3/getComments?appId=74172&ps=1&page=", HttpGet.class);
        }
    }

对于JS渲染的页面需要通过WEBDRIVER进行爬取

首先需要下载DRIVER

请对号入座：http://phantomjs.org/download.html

对于单页面单任务

    public class SeleniumTest extends AbstractSeleniumSpider{
        @Override
        public String getUrl() {
            return "http://huaban.com/";
        }
    
        @Override
        public WebClientConfig getWebClientConfig() {
            return null;
        }
    
        @Override
        public WebClientProcessor<WebClient<String>, String> getProcessor() {
            return new WebClientProcessor<WebClient<String>, String>() {
                @Override
                public String process(WebClient<String> stringWebClient) {
                    WebClient<String> webClient = stringWebClient;
                    System.out.println(webClient.getHtmlOrJson());
                    //拿到Webdriver你还可以做其他的事情,这里可以去看Webdriver的用法了
                    WebDriver webDriver = stringWebClient.getWebDriver();
                    return null;
                }
            };
        }
    
        public static void main(String[] args) {
            System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/Users/songyaheng/Downloads/软件/phantomjs");
            SeleniumTest seleniumTest = new SeleniumTest();
            String html = seleniumTest.run();
            System.out.println(html);
        }
    }
    
多页面多任务

    public class MultiSeleniumTest extends AbstractSeleniumExecuterService{
        public MultiSeleniumTest() {
            super(3, 1000);
        }
    
        @Override
        public WebClientConfig getWebClientConfig() {
            return null;
        }
    
        @Override
        public WebClientProcessor<WebClient<String>, String> getWebClientProcessor() {
            return new WebClientProcessor<WebClient<String>, String>() {
                @Override
                public String process(WebClient<String> stringWebClient) {
                    String html = stringWebClient.getHtmlOrJson();
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(html);
                    return null;
                }
            };
        }
    
        public static void main(String[] args) {
            System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/Users/songyaheng/Downloads/软件/phantomjs");
            MultiSeleniumTest multiSeleniumTest = new MultiSeleniumTest();
            multiSeleniumTest.run(); //多线程
    //        multiSeleniumTest.runAsyn(); // 并行
            multiSeleniumTest.put("http://huaban.com/");
            multiSeleniumTest.put("http://huaban.com/");
            multiSeleniumTest.put("http://huaban.com/");
        }
    }