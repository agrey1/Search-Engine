/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.LinkedList;
import stores.Page;
import stores.VisitedPage;

/**
 *
 * @author Alexander Grey
 */
public class Crawler
{
    Cluster cluster = null;
    Session session = null;
    
    final String[] validDomainExtentions = 
    {
        ".aaa",
        ".aarp",
        ".abb",
        ".abbott",
        ".abogado",
        ".ac",
        ".academy",
        ".accenture",
        ".accountant",
        ".accountants",
        ".aco",
        ".active",
        ".actor",
        ".ad",
        ".ads",
        ".adult",
        ".ae",
        ".aeg",
        ".aero",
        ".af",
        ".afl",
        ".ag",
        ".agency",
        ".ai",
        ".aig",
        ".airforce",
        ".airtel",
        ".al",
        ".allfinanz",
        ".alsace",
        ".am",
        ".amica",
        ".amsterdam",
        ".android",
        ".ao",
        ".apartments",
        ".app",
        ".apple",
        ".aq",
        ".aquarelle",
        ".ar",
        ".aramco",
        ".archi",
        ".army",
        ".arpa",
        ".arte",
        ".as",
        ".asia",
        ".associates",
        ".at",
        ".attorney",
        ".au",
        ".auction",
        ".audio",
        ".auto",
        ".autos",
        ".aw",
        ".ax",
        ".axa",
        ".az",
        ".azure",
        ".ba",
        ".band",
        ".bank",
        ".bar",
        ".barcelona",
        ".barclaycard",
        ".barclays",
        ".bargains",
        ".bauhaus",
        ".bayern",
        ".bb",
        ".bbc",
        ".bbva",
        ".bcn",
        ".bd",
        ".be",
        ".beats",
        ".beer",
        ".bentley",
        ".berlin",
        ".best",
        ".bet",
        ".bf",
        ".bg",
        ".bh",
        ".bharti",
        ".bi",
        ".bible",
        ".bid",
        ".bike",
        ".bing",
        ".bingo",
        ".bio",
        ".biz",
        ".bj",
        ".black",
        ".blackfriday",
        ".bloomberg",
        ".blue",
        ".bm",
        ".bms",
        ".bmw",
        ".bn",
        ".bnl",
        ".bnpparibas",
        ".bo",
        ".boats",
        ".bom",
        ".bond",
        ".boo",
        ".boots",
        ".boutique",
        ".br",
        ".bradesco",
        ".bridgestone",
        ".broadway",
        ".broker",
        ".brother",
        ".brussels",
        ".bs",
        ".bt",
        ".budapest",
        ".build",
        ".builders",
        ".business",
        ".buzz",
        ".bv",
        ".bw",
        ".by",
        ".bz",
        ".bzh",
        ".ca",
        ".cab",
        ".cafe",
        ".cal",
        ".camera",
        ".camp",
        ".cancerresearch",
        ".canon",
        ".capetown",
        ".capital",
        ".car",
        ".caravan",
        ".cards",
        ".care",
        ".career",
        ".careers",
        ".cars",
        ".cartier",
        ".casa",
        ".cash",
        ".casino",
        ".cat",
        ".catering",
        ".cba",
        ".cbn",
        ".cc",
        ".cd",
        ".ceb",
        ".center",
        ".ceo",
        ".cern",
        ".cf",
        ".cfa",
        ".cfd",
        ".cg",
        ".ch",
        ".chanel",
        ".channel",
        ".chat",
        ".cheap",
        ".chloe",
        ".christmas",
        ".chrome",
        ".church",
        ".ci",
        ".cipriani",
        ".cisco",
        ".citic",
        ".city",
        ".cityeats",
        ".ck",
        ".cl",
        ".claims",
        ".cleaning",
        ".click",
        ".clinic",
        ".clothing",
        ".cloud",
        ".club",
        ".clubmed",
        ".cm",
        ".cn",
        ".co",
        ".coach",
        ".codes",
        ".coffee",
        ".college",
        ".cologne",
        ".com",
        ".commbank",
        ".community",
        ".company",
        ".computer",
        ".comsec",
        ".condos",
        ".construction",
        ".consulting",
        ".contractors",
        ".cooking",
        ".cool",
        ".coop",
        ".corsica",
        ".country",
        ".coupons",
        ".courses",
        ".cr",
        ".credit",
        ".creditcard",
        ".creditunion",
        ".cricket",
        ".crown",
        ".crs",
        ".cruises",
        ".csc",
        ".cu",
        ".cuisinella",
        ".cv",
        ".cw",
        ".cx",
        ".cy",
        ".cymru",
        ".cyou",
        ".cz",
        ".dabur",
        ".dad",
        ".dance",
        ".date",
        ".dating",
        ".datsun",
        ".day",
        ".dclk",
        ".de",
        ".deals",
        ".degree",
        ".delivery",
        ".dell",
        ".delta",
        ".democrat",
        ".dental",
        ".dentist",
        ".desi",
        ".design",
        ".dev",
        ".diamonds",
        ".diet",
        ".digital",
        ".direct",
        ".directory",
        ".discount",
        ".dj",
        ".dk",
        ".dm",
        ".dnp",
        ".do",
        ".docs",
        ".dog",
        ".doha",
        ".domains",
        ".doosan",
        ".download",
        ".drive",
        ".durban",
        ".dvag",
        ".dz",
        ".earth",
        ".eat",
        ".ec",
        ".edu",
        ".education",
        ".ee",
        ".eg",
        ".email",
        ".emerck",
        ".energy",
        ".engineer",
        ".engineering",
        ".enterprises",
        ".epson",
        ".equipment",
        ".er",
        ".erni",
        ".es",
        ".esq",
        ".estate",
        ".et",
        ".eu",
        ".eurovision",
        ".eus",
        ".events",
        ".everbank",
        ".exchange",
        ".expert",
        ".exposed",
        ".express",
        ".fage",
        ".fail",
        ".fairwinds",
        ".faith",
        ".family",
        ".fan",
        ".fans",
        ".farm",
        ".fashion",
        ".feedback",
        ".ferrero",
        ".fi",
        ".film",
        ".final",
        ".finance",
        ".financial",
        ".firmdale",
        ".fish",
        ".fishing",
        ".fit",
        ".fitness",
        ".fj",
        ".fk",
        ".flights",
        ".florist",
        ".flowers",
        ".flsmidth",
        ".fly",
        ".fm",
        ".fo",
        ".foo",
        ".football",
        ".forex",
        ".forsale",
        ".forum",
        ".foundation",
        ".fr",
        ".frl",
        ".frogans",
        ".fund",
        ".furniture",
        ".futbol",
        ".fyi",
        ".ga",
        ".gal",
        ".gallery",
        ".game",
        ".garden",
        ".gb",
        ".gbiz",
        ".gd",
        ".gdn",
        ".ge",
        ".gea",
        ".gent",
        ".genting",
        ".gf",
        ".gg",
        ".ggee",
        ".gh",
        ".gi",
        ".gift",
        ".gifts",
        ".gives",
        ".giving",
        ".gl",
        ".glass",
        ".gle",
        ".global",
        ".globo",
        ".gm",
        ".gmail",
        ".gmo",
        ".gmx",
        ".gn",
        ".gold",
        ".goldpoint",
        ".golf",
        ".goo",
        ".goog",
        ".google",
        ".gop",
        ".gov",
        ".gp",
        ".gq",
        ".gr",
        ".grainger",
        ".graphics",
        ".gratis",
        ".green",
        ".gripe",
        ".group",
        ".gs",
        ".gt",
        ".gu",
        ".gucci",
        ".guge",
        ".guide",
        ".guitars",
        ".guru",
        ".gw",
        ".gy",
        ".hamburg",
        ".hangout",
        ".haus",
        ".healthcare",
        ".help",
        ".here",
        ".hermes",
        ".hiphop",
        ".hitachi",
        ".hiv",
        ".hk",
        ".hm",
        ".hn",
        ".hockey",
        ".holdings",
        ".holiday",
        ".homedepot",
        ".homes",
        ".honda",
        ".horse",
        ".host",
        ".hosting",
        ".hoteles",
        ".hotmail",
        ".house",
        ".how",
        ".hr",
        ".hsbc",
        ".hu",
        ".hyundai",
        ".ibm",
        ".icbc",
        ".ice",
        ".icu",
        ".id",
        ".ie",
        ".ifm",
        ".iinet",
        ".il",
        ".im",
        ".immo",
        ".immobilien",
        ".in",
        ".industries",
        ".infiniti",
        ".info",
        ".ing",
        ".ink",
        ".institute",
        ".insure",
        ".int",
        ".international",
        ".investments",
        ".io",
        ".ipiranga",
        ".iq",
        ".ir",
        ".irish",
        ".is",
        ".ist",
        ".istanbul",
        ".it",
        ".itau",
        ".iwc",
        ".jaguar",
        ".java",
        ".jcb",
        ".je",
        ".jetzt",
        ".jewelry",
        ".jlc",
        ".jll",
        ".jm",
        ".jo",
        ".jobs",
        ".joburg",
        ".jp",
        ".jprs",
        ".juegos",
        ".kaufen",
        ".kddi",
        ".ke",
        ".kg",
        ".kh",
        ".ki",
        ".kia",
        ".kim",
        ".kinder",
        ".kitchen",
        ".kiwi",
        ".km",
        ".kn",
        ".koeln",
        ".komatsu",
        ".kp",
        ".kr",
        ".krd",
        ".kred",
        ".kw",
        ".ky",
        ".kyoto",
        ".kz",
        ".la",
        ".lacaixa",
        ".lancaster",
        ".land",
        ".landrover",
        ".lasalle",
        ".lat",
        ".latrobe",
        ".law",
        ".lawyer",
        ".lb",
        ".lc",
        ".lds",
        ".lease",
        ".leclerc",
        ".legal",
        ".lexus",
        ".lgbt",
        ".li",
        ".liaison",
        ".lidl",
        ".life",
        ".lifestyle",
        ".lighting",
        ".limited",
        ".limo",
        ".linde",
        ".link",
        ".live",
        ".lixil",
        ".lk",
        ".loan",
        ".loans",
        ".lol",
        ".london",
        ".lotte",
        ".lotto",
        ".love",
        ".lr",
        ".ls",
        ".lt",
        ".ltd",
        ".ltda",
        ".lu",
        ".lupin",
        ".luxe",
        ".luxury",
        ".lv",
        ".ly",
        ".ma",
        ".madrid",
        ".maif",
        ".maison",
        ".man",
        ".management",
        ".mango",
        ".market",
        ".marketing",
        ".markets",
        ".marriott",
        ".mba",
        ".mc",
        ".md",
        ".me",
        ".media",
        ".meet",
        ".melbourne",
        ".meme",
        ".memorial",
        ".men",
        ".menu",
        ".meo",
        ".mg",
        ".mh",
        ".miami",
        ".microsoft",
        ".mil",
        ".mini",
        ".mk",
        ".ml",
        ".mm",
        ".mma",
        ".mn",
        ".mo",
        ".mobi",
        ".moda",
        ".moe",
        ".moi",
        ".mom",
        ".monash",
        ".money",
        ".montblanc",
        ".mormon",
        ".mortgage",
        ".moscow",
        ".motorcycles",
        ".mov",
        ".movie",
        ".movistar",
        ".mp",
        ".mq",
        ".mr",
        ".ms",
        ".mt",
        ".mtn",
        ".mtpc",
        ".mtr",
        ".mu",
        ".museum",
        ".mutuelle",
        ".mv",
        ".mw",
        ".mx",
        ".my",
        ".mz",
        ".na",
        ".nadex",
        ".nagoya",
        ".name",
        ".navy",
        ".nc",
        ".ne",
        ".nec",
        ".net",
        ".netbank",
        ".network",
        ".neustar",
        ".new",
        ".news",
        ".nexus",
        ".nf",
        ".ng",
        ".ngo",
        ".nhk",
        ".ni",
        ".nico",
        ".ninja",
        ".nissan",
        ".nl",
        ".no",
        ".nokia",
        ".np",
        ".nr",
        ".nra",
        ".nrw",
        ".ntt",
        ".nu",
        ".nyc",
        ".nz",
        ".obi",
        ".office",
        ".okinawa",
        ".om",
        ".omega",
        ".one",
        ".ong",
        ".onl",
        ".online",
        ".ooo",
        ".oracle",
        ".orange",
        ".org",
        ".organic",
        ".osaka",
        ".otsuka",
        ".ovh",
        ".pa",
        ".page",
        ".panerai",
        ".paris",
        ".partners",
        ".parts",
        ".party",
        ".pe",
        ".pet",
        ".pf",
        ".pg",
        ".ph",
        ".pharmacy",
        ".philips",
        ".photo",
        ".photography",
        ".photos",
        ".physio",
        ".piaget",
        ".pics",
        ".pictet",
        ".pictures",
        ".ping",
        ".pink",
        ".pizza",
        ".pk",
        ".pl",
        ".place",
        ".play",
        ".playstation",
        ".plumbing",
        ".plus",
        ".pm",
        ".pn",
        ".pohl",
        ".poker",
        ".porn",
        ".post",
        ".pr",
        ".praxi",
        ".press",
        ".pro",
        ".prod",
        ".productions",
        ".prof",
        ".properties",
        ".property",
        ".protection",
        ".ps",
        ".pt",
        ".pub",
        ".pw",
        ".py",
        ".qa",
        ".qpon",
        ".quebec",
        ".racing",
        ".re",
        ".realtor",
        ".realty",
        ".recipes",
        ".red",
        ".redstone",
        ".rehab",
        ".reise",
        ".reisen",
        ".reit",
        ".ren",
        ".rent",
        ".rentals",
        ".repair",
        ".report",
        ".republican",
        ".rest",
        ".restaurant",
        ".review",
        ".reviews",
        ".rich",
        ".ricoh",
        ".rio",
        ".rip",
        ".ro",
        ".rocher",
        ".rocks",
        ".rodeo",
        ".rs",
        ".rsvp",
        ".ru",
        ".ruhr",
        ".run",
        ".rw",
        ".rwe",
        ".ryukyu",
        ".sa",
        ".saarland",
        ".sakura",
        ".sale",
        ".samsung",
        ".sandvik",
        ".sandvikcoromant",
        ".sanofi",
        ".sap",
        ".sapo",
        ".sarl",
        ".saxo",
        ".sb",
        ".sbs",
        ".sc",
        ".sca",
        ".scb",
        ".schmidt",
        ".scholarships",
        ".school",
        ".schule",
        ".schwarz",
        ".science",
        ".scor",
        ".scot",
        ".sd",
        ".se",
        ".seat",
        ".security",
        ".seek",
        ".sener",
        ".services",
        ".seven",
        ".sew",
        ".sex",
        ".sexy",
        ".sg",
        ".sh",
        ".shiksha",
        ".shoes",
        ".show",
        ".shriram",
        ".si",
        ".singles",
        ".site",
        ".sj",
        ".sk",
        ".ski",
        ".sky",
        ".skype",
        ".sl",
        ".sm",
        ".sn",
        ".sncf",
        ".so",
        ".soccer",
        ".social",
        ".software",
        ".sohu",
        ".solar",
        ".solutions",
        ".sony",
        ".soy",
        ".space",
        ".spiegel",
        ".spreadbetting",
        ".sr",
        ".srl",
        ".st",
        ".stada",
        ".starhub",
        ".statoil",
        ".stc",
        ".stcgroup",
        ".stockholm",
        ".studio",
        ".study",
        ".style",
        ".su",
        ".sucks",
        ".supplies",
        ".supply",
        ".support",
        ".surf",
        ".surgery",
        ".suzuki",
        ".sv",
        ".swatch",
        ".swiss",
        ".sx",
        ".sy",
        ".sydney",
        ".systems",
        ".sz",
        ".tab",
        ".taipei",
        ".tatamotors",
        ".tatar",
        ".tattoo",
        ".tax",
        ".taxi",
        ".tc",
        ".td",
        ".team",
        ".tech",
        ".technology",
        ".tel",
        ".telefonica",
        ".temasek",
        ".tennis",
        ".tf",
        ".tg",
        ".th",
        ".thd",
        ".theater",
        ".theatre",
        ".tickets",
        ".tienda",
        ".tips",
        ".tires",
        ".tirol",
        ".tj",
        ".tk",
        ".tl",
        ".tm",
        ".tn",
        ".to",
        ".today",
        ".tokyo",
        ".tools",
        ".top",
        ".toray",
        ".toshiba",
        ".tours",
        ".town",
        ".toyota",
        ".toys",
        ".tr",
        ".trade",
        ".trading",
        ".training",
        ".travel",
        ".trust",
        ".tt",
        ".tui",
        ".tv",
        ".tw",
        ".tz",
        ".ua",
        ".ubs",
        ".ug",
        ".uk",
        ".university",
        ".uno",
        ".uol",
        ".us",
        ".uy",
        ".uz",
        ".va",
        ".vacations",
        ".vana",
        ".vc",
        ".ve",
        ".vegas",
        ".ventures",
        ".versicherung",
        ".vet",
        ".vg",
        ".vi",
        ".viajes",
        ".video",
        ".villas",
        ".vin",
        ".virgin",
        ".vision",
        ".vista",
        ".vistaprint",
        ".viva",
        ".vlaanderen",
        ".vn",
        ".vodka",
        ".vote",
        ".voting",
        ".voto",
        ".voyage",
        ".vu",
        ".wales",
        ".walter",
        ".wang",
        ".watch",
        ".webcam",
        ".website",
        ".wed",
        ".wedding",
        ".weir",
        ".wf",
        ".whoswho",
        ".wien",
        ".wiki",
        ".williamhill",
        ".win",
        ".windows",
        ".wine",
        ".wme",
        ".work",
        ".works",
        ".world",
        ".ws",
        ".wtc",
        ".wtf",
        ".xbox",
        ".xerox",
        ".xin",
        ".xn",
        ".xperia",
        ".xxx",
        ".xyz",
        ".yachts",
        ".yamaxun",
        ".yandex",
        ".ye",
        ".yodobashi",
        ".yoga",
        ".yokohama",
        ".youtube",
        ".yt",
        ".za",
        ".zara",
        ".zip",
        ".zm",
        ".zone",
        ".zuerich",
        ".zw"
    };
            
    public Crawler(Cluster cluster)
    {
        this.cluster = cluster;
        this.session = cluster.connect("search");
        this.crawl();
    }
    
    private boolean containsValidDomainExtention(String url)
    {
        for(String extention : validDomainExtentions)
        {
            if(url.contains(extention))
            {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean hasValidEnd(String url)
    {
        String[] invalid = {".css", ".png", ".jpg", ".ogg", ".mp3"};

        for(String notValid : invalid)
        {
            if(url.endsWith(notValid) == true)
            {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isNumber(char character)
    {
        char[] numbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '$', '£', '€'};
        
        for(char number : numbers)
        {
            if(character == number)
            {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean isAlphanumeric(String str) 
    {
        char[] characters = str.toCharArray();
        
        for(char current : characters) 
        {
            if(Character.isLetter(current) == false && isNumber(current) == false)
            {
                return false;
            }
        }
        
        return true;
    }
    
    private void saveLink(String url)
    {
        PreparedStatement ps = this.session.prepare("SELECT url FROM pages WHERE url = ?;");
        BoundStatement boundStatement = new BoundStatement(ps);
        ResultSet result = this.session.execute(boundStatement.bind(url));
        
        if(result.isExhausted())
        {
            ps = this.session.prepare("INSERT INTO links (url) VALUES (?) IF NOT EXISTS;");
            boundStatement = new BoundStatement(ps);
            this.session.execute(boundStatement.bind(url));
        }
    }
    
    private Page getPage(String url)
    {
        Page page = new Page(url);
        
        try
        {
            URL website = new URL(url);
            
            URLConnection connection = website.openConnection();
            //Set timeouts to 5 seconds
            connection.setConnectTimeout(5 * 1000);
            connection.setReadTimeout(5 * 1000);
            
            String domain = website.getHost();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            String line;
            while((line = in.readLine()) != null)
            {
                String lowLine = line.toLowerCase();
                
                if(lowLine.contains("<title>") == true && lowLine.contains("</title>") == true)
                {
                    String title = line.substring(lowLine.indexOf("<title>") + 7, lowLine.indexOf("</title>"));
                    page.setTitle(title);
                }
                
                if(lowLine.contains("<a href=") == true)
                {
                    int pos = lowLine.indexOf("<a href=\"") + 9;
                    String link = line.substring(pos, lowLine.indexOf('"', pos + 1));
                    String lowLink = link.toLowerCase();
                    
                    if(!link.equals("") && hasValidEnd(lowLink) && !link.contains("#"))
                    {
                        if(lowLink.startsWith("//"))
                        {
                            link = link.substring(0, 2);
                            int count = 0;
                            while(link.charAt(count + 1) == '/' && (count + 1) < link.length())
                            {
                                link = link.substring(1, link.length() - 1);
                            }
                        }
                        
                        if(link.startsWith("/"))
                        {
                            link = domain.concat(link);
                        }
                        
                        if(link.toLowerCase().startsWith("http") == false)
                        {
                            if(!link.startsWith("/") && link.contains("/") && link.substring(0, link.indexOf('/')).contains(".") == false)
                            {
                                link = url.concat("/" + link);
                            }
                            else
                            {
                                link = "http://".concat(link);
                            }
                        }
                        
                        if(containsValidDomainExtention(link.toLowerCase()))
                        {
                            while(link.endsWith("/") == true)
                            {
                                link = link.substring(0, link.length() - 1);
                            }
                            
                            int count = 0;
                            for(int i = 0; i < link.length(); i++)
                            {
                                if(link.charAt(i) == '/')
                                {
                                    count++;
                                }
                            }
                            
                            if(count < 10)
                            {
                                saveLink(link);
                            }
                        }
                    }
                }
                
                //Get all words from the current line
                String[] words = lowLine.split(" ");
                for(String word : words)
                {
                    word = word.trim();
                    if(!word.equals("") && isAlphanumeric(word) == true)
                    {
                        page.addWord(word);
                    }
                }
            }
            
            in.close();
        }
        catch(Exception e)
        {
            page = null;
        }
        
        if(page != null)
        {
            if(page.getTitle() != null)
            {
                PreparedStatement ps = session.prepare("INSERT INTO pages (url, title, words, time) VALUES (?, ?, ?, dateof(now()));");
                BoundStatement boundStatement = new BoundStatement(ps);
                session.execute(boundStatement.bind(url, page.getTitle(), page.getWords()));
            }
        }
        
        //PreparedStatement ps = session.prepare("UPDATE search.links SET visited = true WHERE url = ?;");
        //BoundStatement boundStatement = new BoundStatement(ps);
        //session.execute(boundStatement.bind(url));
        
        PreparedStatement ps = session.prepare("DELETE FROM links WHERE url = ?;");
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute(boundStatement.bind(url));
        
        return page;
    }
    
    private void crawl()
    {
        LinkedList<VisitedPage> recentlyVisited = new LinkedList<>();
        
        //Insert a "first page" if required
        String firstPage = "http://www.ovh.co.uk/";
        
        PreparedStatement ps = session.prepare("SELECT url FROM links WHERE url = ?;");
        BoundStatement boundStatement = new BoundStatement(ps);
        ResultSet result = session.execute(boundStatement.bind(firstPage));
        
        if(result.isExhausted())
        {
            ps = session.prepare("INSERT INTO links (url) VALUES (?);");
            boundStatement = new BoundStatement(ps);
            session.execute(boundStatement.bind(firstPage));
        }
        
        while(true)
        {
            SimpleStatement query = new SimpleStatement("SELECT url FROM links LIMIT 1;");
            result = session.execute(query);
            
            if(result.isExhausted())
            {
                System.out.println("No more pages to crawl.");
                break;
            }
            else
            {
                String url = result.one().getString("url");
                ps = session.prepare("DELETE FROM links WHERE url = ?;");
                boundStatement = new BoundStatement(ps);
                session.execute(boundStatement.bind(url));
                
                System.out.println("Crawling [" + url + "]");
                
                try
                {
                    URL current = new URL(url);
                    
                    long now = System.currentTimeMillis() / 1000l;
                    int count = 0;

                    for (Iterator<VisitedPage> iterator = recentlyVisited.iterator(); iterator.hasNext();) 
                    {
                        VisitedPage visited = iterator.next();
                        if(now - visited.getTime() > 180)
                        {
                            iterator.remove();
                        }
                        else if(visited.getURL().equals(current.getHost()))
                        {
                            count++;
                        }
                    }
                    
                    if(count < 10)
                    {
                        getPage(url);

                        VisitedPage visited = new VisitedPage(current.getHost());
                        recentlyVisited.add(visited);
                    }
                }
                catch(Exception e)
                {
                
                }
            }
        }
    }
}