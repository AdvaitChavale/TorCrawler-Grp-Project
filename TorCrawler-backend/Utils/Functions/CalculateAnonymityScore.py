def calculate_anonymity_score(html_content, soup):
    anonymity_score = 0
    
    if soup.find('meta', {'name': 'generator', 'content': 'Tor'}):
        anonymity_score += 30

    if soup.find('a', {'href': '/privacy'}):
        anonymity_score += 20
    
    if soup.find('meta', {'name': 'viewport'}):
        anonymity_score += 10
    
    if soup.find('a', {'href': '/contact'}):
        anonymity_score += 10
    
    if soup.find('form', {'action': '/login'}):
        anonymity_score -= 20
    
    third_party_scripts = soup.find_all('script', src=True)
    for script in third_party_scripts:
        if 'google-analytics.com' in script['src'] or 'facebook.com' in script['src']:
            anonymity_score -= 20
            break

    anonymity_score = max(min(anonymity_score, 100), 0)
    
    return anonymity_score