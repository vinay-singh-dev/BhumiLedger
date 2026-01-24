1.Purpose:
the problem of land records being kept only in govt websites is solved by BhumiLedger because in BhumiLedger Buyer,Seller,verfication by (Gram panchayat / sabha) are kept anyone can see the records of land.
It does not solve the corruption and bribes taken in land selling and it does not replaces govt sites of land records.
it protects land records of rightful owner and keeps the tabs of previous owners or buyers with (gram panchayat / gram sabha) verfication on every buying and selling.

2. Core Principles (invariants)
(Append-only history) it means that past records of land buyers and sellers are kept and they cannot be edited or removed only new buyers and sellers can se added.
(Verified records immutable) records cannot me modified or edited only new records can be kept
(No silent authority overrides) any authority cannot change land ownership they can only verify it so other buyer can see that this land has no disputes and its good to buy.
(External records = evidence, not truth) it means external documents and records are evidence that the land belongs to that person or that transaction of buying or selling is valid.

3.Actors

Community member

(Gram Sabha / Panchayat) these authorities cannot edit land records or external documents they can only verify or unverify that land belongs to that person or not and is the land good to sell by seller and that land does not have any disputes loans,emi,previous ownership claim cases etc.

(State authority) now state authorities are future concept also state authorities cannot edit or change anything in BhumiLedger they can just verify and it will add a verification badge that this land is verified.

Auditor
For each: allowed + forbidden actions
(gram sabha / panchayat) this authority can add a verification badge if the land is not any dispute and that badge will say that this land is good to buy.
but this authority cannot edit land records or identities.

(state authority) also this is future concepts but i was thinking that this will also add a verfication badge layer that will serve the same purpose as (gram panchayat / gram sabha ).
this authority cannot edit or change land records 

4.Core Domain Concepts

LandParcel 

OwnershipClaim any rightful owner can submit his claim with documents that contains previous owner, when bought, amount of transaction, bank records, etc.

ConsentRecord concent record of the owner and seller with their documents for verification of rightful owner.

RegistryEntry
Meaning only. No fields.

5.Domain Actions

Submit claim any rightful owner can submit his claim with documents that contains previous owner, when bought, amount of transaction, bank records, etc.

(Verify / reject) gram panchat / sabha can reject or verify the claim based on documents both from seller and buyer.

Dispute any dispute can be resolved with verfication of gram sabha / pachayat badge and verification of documents of rightful ownership claim

Transfer
Preconditions + outcomes.


6.Ownership lifecycle

Step-by-step, plain English
seller logins in portal
seller adds the land documents and submit to verification by gram panchayat  / sabha for selling land.
gram panchayat / sabha can login and  verify if the selling claim is real with cross verification with documents after verification badge the land is for selling
buyer logins and show their intrest in land buying  enters contact details for seller to contact.
buyer meets with seller the transaction happens and then buyer and seller both submit the documents from their portals
again gram panchayat / sabha verifies if the documents are correct and transaction happend.

7.Blockchain boundary

Used for hash to store land record.

NOT used for for transaction in bitcoin.

8.Failure scenarios 

(Conflicting claims) these can be created if (gram panchayat / sabha) will verify the record of land claim also the seller and buyer both should sumbit documents which will be revived if the conflicat arises.

(Authority abuse) gram panchayat / sabha is not a one person that can do anything its a bunch of people also this authority has only the power to add a verification badge not to change ownership of land.

(Forged evidence) forged evidence is a threat but its a lot of work because there are multiple authorities involved.
