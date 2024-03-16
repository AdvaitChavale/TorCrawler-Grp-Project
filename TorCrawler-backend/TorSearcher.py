def torSearcher(url):
    import requests
    def get_tor_session():
        session = requests.session()
        session.proxies = {'http':  'socks5h://127.0.0.1:9050',
                           'https': 'socks5h://127.0.0.1:9050'}
        return session

    session = get_tor_session()

    print("Getting ...", url)
    result = session.get(url).text
    return result

url = "http://777777ipar4tzwxylsznx6o7trdgo5dgirkjx3orufjjnenjbe5xheyd.onion"

res = torSearcher(url)

### Scrapping part

from bs4 import BeautifulSoup
soup = BeautifulSoup(res, 'html.parser')

sentence = soup.find('title').get_text()
parts = sentence.split('–')

from fastapi import FastAPI
from fastapi.params import Body

app = FastAPI()

@app.post("/onion_search")
def onion_search(payload : dict = Body(...)):
    try:
        import requests

        url = "http://ip-api.com/json/"
        key = requests.get(url)
        #print(key.text)
        if "Croatia" in key.text or "Zagreb" in key.text or "Hrvatska" in key.text:
            print("Your VPN might not be on !!")
            safe = False
        else:
            safe = True

        import json
        if safe == True:
            import ahmiascraper
            data = ahmiascraper.Scraper(payload['url'])
            # print(data)
            return data
        else:
            print("IP change failed, try again later.")
            
    except Exception as e:
        return {"error": str(e)}

@app.post("/onion_analysis/")
def onion_analysis(payload: dict = Body(...)):
    print(payload)
    try:
        url = payload['url']
        res = torSearcher(url)
        soup = BeautifulSoup(res, 'html.parser')
        sentence = soup.find('title').get_text()
        parts = sentence.split('–')

        images = [img['src'] for img in soup.find_all('img', src=True)]

        h2_tags = [tag.get_text() for tag in soup.find_all('h2')]
        links = [{"text": link.get_text(), "href": link.get('href')} for link in soup.find_all('a')]

        data = {
            "title": parts[0].strip() if len(parts) > 0 else None,
            "market_place": parts[1].strip() if len(parts) > 1 else None,
            "images": images if len(images) > 0 else None,
            "h2_tags": h2_tags,
            "links": links
        }
        
        return data
        
    except Exception as e:
        return {"error": str(e)}
    
    
if __name__ == '__main__':
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)