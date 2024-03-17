def extract_image_info(html_content, soup):
    image_info_list = []


    img_tags = soup.find_all('img')

    for img_tag in img_tags:
        url = img_tag.get('src')
        title = img_tag.get('title')
        alt = img_tag.get('alt')
        

        image_info_list.append({'url': url, 'title': title, 'alt': alt})

    return image_info_list