def analyze_threat(html_content, soup):
    threat_level = 0

    scripts = soup.find_all('script')
    for script in scripts:
        if script.get('src'):
            threat_level += 0  
        else:
            
            keywords = ['malware', 'phishing', 'exploit', 'trojan', 'virus', 'ransomware', 'spyware', 'keylogger', 'cryptojacking', 'backdoor', 'rootkit', 'botnet', 'adware', 'worm', 'social engineering']
            for keyword in keywords:
                if keyword in script.text.lower():
                    threat_level += 1  


    iframes = soup.find_all('iframe')
    for iframe in iframes:
        if iframe.get('src'):
            threat_level += 0  
  
            iframe_src = iframe.get('src').lower()
            for keyword in keywords:
                if keyword in iframe_src:
                    threat_level += 2 

 
    forms = soup.find_all('form')
    for form in forms:
        action = form.get('action')
        if action and 'http' in action:
            threat_level += 1  


    links = soup.find_all('a')
    for link in links:
        href = link.get('href')
        if href and 'http' in href:
            threat_level += 1  
           
            link_href = href.lower()
            for keyword in keywords:
                if keyword in link_href:
                    threat_level += 1  

    meta_tags = soup.find_all('meta')
    for meta_tag in meta_tags:
        for attribute in ['name', 'content']:
            if attribute in meta_tag.attrs and any(keyword in meta_tag[attribute].lower() for keyword in keywords):
                threat_level += 1  

    return min(threat_level, 100)