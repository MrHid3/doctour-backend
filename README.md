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
  - **/register** - tu wyślij
     ```
     {
         "username": "bob",
         "password": "verySaf3Password",
         "role": "ROLE_CUSTOMER"|"ROLE_DOCTOR"
     }
     ```
    żeby zarejestrować użytkownika  
    może zwrócić: 
      - User created succesfully
      - Invalid role - rola nie jest w postaci "ROLE_CUSTOMER"|"ROLE_DOCTOR"
      - Username taken
      - Invalid password - hasło jest za łatwe (musi zawierać wielką i małą literę, cyfrę i znak specjalny i conajmniej 8 znaków)
          
  - **/login** - tu wyślij
        ```
        {
            "username": "bob",
            "password": "verySaf3Password"
        }
        ```  
        może zwrócić:
        - refresh token
        - Username not found
        - Wrong username or password - nie ma użytkownika o takiej nazwie i haśle
  - **/refresh** - tutaj prosisz o nowego access tokena (wyguglaj JWT refresh/access jak nie wiesz o co cho), zwraca access token
  - **/logout** - invaliduje ciacha, wylogowywując użytkownika