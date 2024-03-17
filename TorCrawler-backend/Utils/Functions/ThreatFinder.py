def threat_finder(header_tags):
    import openai
    
    openai.api_key = "###"
    threats = f'''{header_tags} 
    These are the header tags of a onion website, I want you to find threat words in them, also give a threat rating on scale 1 to 3 to each threats and give the answer in json format use threat_title and threat_rating'''
    completion = openai.ChatCompletion.create(model="gpt-3.5-turbo", messages=[{"role": "user", "content": threats}])
    threats_all = completion.choices[0].message.content
    
    return threats_all