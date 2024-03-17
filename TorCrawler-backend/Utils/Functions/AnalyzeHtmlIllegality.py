def analyze_html_illegality(html_content, soup):
    legality_rating = 0


    keywords_illegal = ['illegal', 'unlawful', 'contraband', 'prohibited', 'black market', 'forbidden', 'banned', 'restricted',
                        'piracy', 'counterfeit', 'fraud', 'smuggling', 'trafficking', 'bootleg', 'scam', 'phishing',
                        'money laundering', 'embezzlement', 'extortion', 'cybercrime', 'hacking', 'identity theft']
    for keyword in keywords_illegal:
        if keyword in html_content.lower():
            legality_rating += 4 

    meta_tags = soup.find_all('meta')
    for meta_tag in meta_tags:
        for attribute in ['name', 'content']:
            if attribute in meta_tag.attrs and any(keyword in meta_tag[attribute].lower() for keyword in keywords_illegal):
                legality_rating += 2 

  
    links = soup.find_all('a', href=True)
    for link in links:
        href = link['href']
        if any(keyword in href.lower() for keyword in keywords_illegal):
            legality_rating += 2  


    image_tags = soup.find_all('img', src=True)
    for image_tag in image_tags:
        src = image_tag['src']
        if any(keyword in src.lower() for keyword in keywords_illegal):
            legality_rating += 3  

    return min(legality_rating, 100)