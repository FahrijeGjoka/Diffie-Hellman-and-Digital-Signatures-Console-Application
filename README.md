# Diffie-Hellman-and-Digital-Signatures-Console-Application


## Përshkrimi

Ky projekt realizon një kanal komunikimi të sigurt mes klientit dhe serverit duke përdorur:

- **Diffie-Hellman** për shkëmbimin e çelësave.
- **RSA** për firmën dixhitale.
- **AES** për enkriptimin simetrik të mesazheve.

## Pjesët Kryesore

- `DiffieHellmanUtil`: Gjeneron dhe menaxhon parametrat dhe çelësat Diffie-Hellman.
- `DigitalSignatureUtil`: Krijon dhe verifikon firma dixhitale me RSA.
- `AESEncryptionUtil`: Enkripton dhe dekripton të dhëna me AES.
- `SecureServer`: Serveri që ndan parametrat, firmos mesazhet dhe bën enkriptimin/dekriptimin.
- `SecureClient`: Klienti që komunikon me serverin dhe përdor të njëjtat protokolle sigurie.

## Udhëzime Ekzekutimi

1. Kompilo klasat:
   ```bash
   javac *.java
   
2. Nis Serverin
   java SecureServer

3. Nis klientin në një terminal tjetër:
   java SecureClient


### Rezulati i pritshem ne Console:

#### Nga ana e Serverit
Server: Listening on port 5000...
Server: Client connected.
Server: Shared secret established: 1027c8b6955471e56d88026026e04bf757258c4483299699e46cbe41ff528f0551b15fea15c606a2476f06fc37d4bda2d2c6047a548574fd07e718f5759d36b2
Server received encrypted message: Pershendetje nga klienti!

#### Nga ana e klientit
Client: Connected to server.
Client: Shared secret established: 1027c8b6955471e56d88026026e04bf757258c4483299699e46cbe41ff528f0551b15fea15c606a2476f06fc37d4bda2d2c6047a548574fd07e718f5759d36b2
Client: Signature is valid. Trusted communication established.
Client received encrypted message: This is a secret message
Client sent encrypted message: Pershendetje nga klienti!






