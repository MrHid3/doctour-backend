# Doctour

*(nazwa robocza)*

## Rzeczy do zrobienia

- [x] logowanie/rejestracja/cookie/whatever
- [ ] doktor ustawia lokalizacje/grafik/type shit
- [ ] użytkownik może się do niego umówić
- [ ] anulowanie itd, system powiadomień
- [ ] szukanie lekarzy po lokalizacji
- [ ] powiadomienia do pulpitu w stylu "hej jutro operacja kolan"???
- [ ] gift cardy???

## Endpointy:

- ### /api/auth (autentykacja):
    - **POST /register** - tu wyślij
       ```
       {
            "email": "bob.smith@gmail.com"
            "username": "bob smith",
            "password": "verySaf3Password",
            "role": "ROLE_CUSTOMER"|"ROLE_DOCTOR",
            "genderId": 1
       }
       ```
      żeby zarejestrować użytkownika. Wysyła do nowo zarejestrowanego użytkownika maila z linkiem aktywacyjnym  (
      /activateAccount?token=ugrigiyvuy4v4yivi4y)  
      błędy:
        - ROLE_INVALID - nie ma takiej roli
        - GENDER_INVALID - nie ma takiej płci w bazie
    - **POST /activate**  - tu wyślij
        ```
        {
        token: '3jgvkwhbkhn3fbveiygv4uiorvv4gf4g3"
      }
      ```
      token jest podany jako parametr GET w linku aktywacyjnym. Konto zostanie wtedy aktywowane

    - **POST /login** - tu wyślij
      ```
      {
          "email": "bob",
          "password": "verySaf3Password"
      }
      ```  
      przy pomyślnym logowaniui dopina ciacha accessToken i refreshToken
      błędy:
        - USER_NOT_FOUND - nie ma użytkownika o takiej nazwie i haśoe
        - USER_NOT_ACTIVE
        - **/refresh** - tutaj prosisz o nowego access tokena (wyguglaj JWT refresh/access jak nie wiesz o co cho  
          wysyłasz pustego requesta i on ustawia ciacho accessToken
          błędy:
            - TOKEN_INVALID - wysłanego accessTokena nie ma w bazie
    - **POST /logout** - invaliduje ciacha, efektywnie wylogowywując użytkownika
- ## /api/location
    - **GET** - zwraca wszystkie lokalizacje
    - **POST**
  ```
  {
    name:  "Gabinety Dentystystyczny Szeroki Uśmiech",
    description: "Jeden z top 60 gabinetów w powiacie",
    latitude: 50.0967598,
    longitude: 20.1745166
  }
    ```
  zapisuje lokalizację w bazie danych