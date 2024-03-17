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