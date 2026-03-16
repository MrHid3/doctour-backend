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
         "role": "USER"|"DOCTOR"
     }
     ```
    żeby zarejestrować użytkownika
    - **/login** - tu wyślij
     ```
     {
         "username": "bob",
         "password": "verySaf3Password"
     }
     ```
    dostajesz z powrotem refresh token
  - **/refresh** - tutaj prosisz o nowego access tokena (wyguglaj JWT refresh/access jak nie wiesz o co cho), zwraca access token
  - **/logout** - invaliduje ciacha, wylogowywując użytkownika