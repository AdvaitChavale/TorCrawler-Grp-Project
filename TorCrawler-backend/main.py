from fastapi import FastAPI
from fastapi.params import Body
from Utils.Functions.TorSearcher import torSearcher
from Utils.Functions.ExtractImageInfo import extract_image_info
from Utils.Functions.AnalyzePrivacy import analyze_privacy
from Utils.Functions.AnalyzeHtmlIllegality import analyze_html_illegality
from Utils.Functions.AnalyzeThreat import analyze_threat
from Utils.Functions.CalculateAnonymityScore import calculate_anonymity_score
from Utils.Functions.ThreatFinder import threat_finder
from bs4 import BeautifulSoup

app = FastAPI()

@app.post("/onion_analysis/")
def onion_analysis(payload: dict = Body(...)):
    
    url = payload['url']
    res = torSearcher(url)

    soup = BeautifulSoup(res, 'html.parser')
    sentence = soup.find('title').get_text()
    parts = sentence.split('–')

    privacy_risk = analyze_privacy(res, soup)
    legal_risk = analyze_html_illegality(res, soup)
    threat_risk = analyze_threat(res, soup)
    anonymity_score = calculate_anonymity_score(res, soup)

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
        "anonymity_score" : anonymity_score,
        "identified_threats": json_identified_threats,
        "header_tags": header_tags,
        "links": links,
        "images": jsonL_image_data,
    }
    
    
    return data


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
            import Utils.onion_searcher as onion_searcher
            data = onion_searcher.Scraper(payload['search_term'])
            # print(data)
            return data
        else:
            print("IP change failed, try again later.")
            
    except Exception as e:
        return {"error": str(e)}