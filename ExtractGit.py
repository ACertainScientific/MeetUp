import requests
from bs4 import BeautifulSoup

if __name__ == "__main__":
    url = 'https://github.com/ACertainScientific/MeetUp-FrontEnd/commits?author=yichen0104'
    strhtml = requests.get(url)
    soup = BeautifulSoup(strhtml.text, 'html.parser')
    data = soup.find_all('a', class_ ='Link--primary text-bold js-navigation-open markdown-title')
    for d in data:
        print('https://github.com' + d['href'])
