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
            data = ahmiascraper.Scraper(payload['search_term'])
            # print(data)
            return data
        else:
            print("IP change failed, try again later.")
            
    except Exception as e:
        return {"error": str(e)}
    
    

@app.post("/onion_analysis/")
def onion_analysis(payload: dict = Body(...)):
    print(payload)
    url = payload['url']
    res = torSearcher(url)
    
    from bs4 import BeautifulSoup
    
    soup = BeautifulSoup(res, 'html.parser')
    sentence = soup.find('title').get_text()
    parts = sentence.split('–')

    privacy_risk = analyze_privacy(res, soup)
    legal_risk = analyze_html_illegality(res, soup)
    threat_risk = analyze_threat(res, soup)

    average_risk = (privacy_risk+legal_risk+threat_risk)/3

    if average_risk < 33:
        security_risk_title = "Low Security Risk site"
    elif average_risk < 66:
        security_risk_title = "Medium Security Risk site"
    else:
        security_risk_title = "High Security Risk site"

    

    header_tags = [tag.get_text() for tag in soup.find_all(['h1', 'h2'])]
    links = [{"text": link.get_text(), "href": link.get('href')} for link in soup.find_all('a')]
    
    import json
    identified_threats = threat_finder(header_tags)
    json_identified_threats = json.loads(identified_threats)
    
    image_info_list = extract_image_info(res, soup)
    jsonD_image_data = json.dumps(image_info_list, indent=2)
    jsonL_image_data = json.loads(jsonD_image_data)

    data = {
        "security_risk_title": security_risk_title,
        "url" : url, 
        "title": parts[0].strip() if len(parts) > 0 else None,
        "market_place": parts[1].strip() if len(parts) > 1 else None,
         "privacy_risk": privacy_risk,
        "legal_risk": legal_risk,
        "threat_risk": threat_risk,
        "identified_threats": json_identified_threats,
        "header_tags": header_tags,
        "links": links,
        "images": jsonL_image_data,
    }
    
    
    return data



def analyze_privacy(html_content, soup):
    privacy_rating = 0
    
    
    tracking_scripts = soup.find_all('script', {'src': True})
    if tracking_scripts:
        privacy_rating += 5


    forms = soup.find_all('form')
    for form in forms:
        input_fields = form.find_all('input')
        if any(input_field.get('type') == 'email' for input_field in input_fields):
            privacy_rating += 5 
        if any(input_field.get('type') == 'password' for input_field in input_fields):
            privacy_rating += 10

    meta_tags = soup.find_all('meta')
    for meta_tag in meta_tags:
        if 'privacy' in meta_tag.get('name', '').lower() or 'privacy' in meta_tag.get('content', '').lower():
            privacy_rating += 2  

    return min(privacy_rating, 100)  


def analyze_html_illegality(html_content, soup):
    legality_rating = 0


    keywords_illegal = ['illegal', 'unlawful', 'contraband', 'prohibited', 'black market', 'forbidden', 'banned', 'restricted',
                        'piracy', 'counterfeit', 'fraud', 'smuggling', 'trafficking', 'bootleg', 'scam', 'phishing',
                        'money laundering', 'embezzlement', 'extortion', 'cybercrime', 'hacking', 'identity theft']
    for keyword in keywords_illegal:
        if keyword in html_content.lower():
            legality_rating += 4  # Increment rating if keyword is found

    # Check meta tags and keywords
    meta_tags = soup.find_all('meta')
    for meta_tag in meta_tags:
        for attribute in ['name', 'content']:
            if attribute in meta_tag.attrs and any(keyword in meta_tag[attribute].lower() for keyword in keywords_illegal):
                legality_rating += 2  # Increment rating if keyword is found in meta tag

    # Check for links pointing to illegal content
    links = soup.find_all('a', href=True)
    for link in links:
        href = link['href']
        if any(keyword in href.lower() for keyword in keywords_illegal):
            legality_rating += 2  # Increment rating if link points to illegal content

    # Check for images with suspicious file names
    image_tags = soup.find_all('img', src=True)
    for image_tag in image_tags:
        src = image_tag['src']
        if any(keyword in src.lower() for keyword in keywords_illegal):
            legality_rating += 3  # Increment rating if image src contains keywords indicating illegality

    return min(legality_rating, 100)




def analyze_threat(html_content, soup):
    threat_level = 0
    # Check for JavaScript code
    scripts = soup.find_all('script')
    for script in scripts:
        if script.get('src'):
            threat_level += 0  # Increment threat level if external JavaScript file is included
        else:
            # Count the number of occurrences of certain keywords indicating potential threats
            keywords = ['malware', 'phishing', 'exploit', 'trojan', 'virus', 'ransomware', 'spyware', 'keylogger', 'cryptojacking', 'backdoor', 'rootkit', 'botnet', 'adware', 'worm', 'social engineering']
            for keyword in keywords:
                if keyword in script.text.lower():
                    threat_level += 1  # Increment threat level if keyword is found

    # Check for iframes
    iframes = soup.find_all('iframe')
    for iframe in iframes:
        if iframe.get('src'):
            threat_level += 0  # Increment threat level if iframe src is present (potential for malicious content)
            # Check keywords in iframe src
            iframe_src = iframe.get('src').lower()
            for keyword in keywords:
                if keyword in iframe_src:
                    threat_level += 2  # Increment threat level if keyword is found in iframe src

    # Check form actions
    forms = soup.find_all('form')
    for form in forms:
        action = form.get('action')
        if action and 'http' in action:
            threat_level += 1  # Increment threat level for form actions going to external URLs

    # Check links
    links = soup.find_all('a')
    for link in links:
        href = link.get('href')
        if href and 'http' in href:
            threat_level += 1  # Increment threat level for external links
            # Check keywords in link href
            link_href = href.lower()
            for keyword in keywords:
                if keyword in link_href:
                    threat_level += 1  # Increment threat level if keyword is found in link href

    # Check meta tags and keywords
    meta_tags = soup.find_all('meta')
    for meta_tag in meta_tags:
        for attribute in ['name', 'content']:
            if attribute in meta_tag.attrs and any(keyword in meta_tag[attribute].lower() for keyword in keywords):
                threat_level += 1  # Increment threat level if keyword is found in meta tag

    return min(threat_level, 100)


def threat_finder(header_tags):
    import openai
    
    openai.api_key = "###"
    threats = f'''{header_tags} 
    These are the header tags of a onion website, I want you to find threat words in them, also give a threat rating on scale 1 to 3 to each threats and give the answer in json format use threat_title and threat_rating'''
    completion = openai.ChatCompletion.create(model="gpt-3.5-turbo", messages=[{"role": "user", "content": threats}])
    threats_all = completion.choices[0].message.content
    
    return threats_all


def extract_image_info(html_content, soup):
    image_info_list = []

    # Parse HTML content using BeautifulSoup
    # Find all img tags
    img_tags = soup.find_all('img')

    # Extract URL, title, and alt attributes from each img tag
    for img_tag in img_tags:
        url = img_tag.get('src')
        title = img_tag.get('title')
        alt = img_tag.get('alt')
        
        # Append image info to the list
        image_info_list.append({'url': url, 'title': title, 'alt': alt})

    return image_info_list