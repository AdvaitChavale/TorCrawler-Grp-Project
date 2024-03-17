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