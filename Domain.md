1.Purpose:
the problem is land records being kept only in govt websites and those records are managed by local authority no one can see all these records in BhumiLedger we can see all these records are publically available. 

BhumiLedger exists so the documents are publically available and the documents cannot be edited or removed once entered.

this reduces risks of documents forging , fake claims etc.

2. Core Principles (invariants)
(Append-only history) it means that past records of land owners are kept and they cannot be removed only new owners can be  added.
(Verified records immutable) records cannot me modified or edited only new records can be kept and people can see records.

3.Actors

Community member = community members can only see records and apply to be a claiment for a land when they have proper documents.
community members cannot verify claim.
community memebrs cannot transfer ownership alone they need authorities.
community members cannot delete or edit history they can only apply its up to authorities.

(Gram Sabha / Panchayat) = these authorities they can verify a claim of a claiment with proper documents.
these are authorities they cannot create a claim.
these authorities cannot transfer ownership.
these authorities cannot delete or edit history.
these authorities can only review documents and verify a claim or unverify it based on documents.

(State authority) now state authorities they can just verify that this land is verified after gram panchayat / sabha verification.
these authorities cannot creat a claim.
these authorities cannot transfer ownership.
these authorities cannot delet or edit history.

Auditor
For each: allowed + forbidden actions
(gram sabha / panchayat) this authority can add a verification  if the land is not any dispute and that badge will say that this land is good to buy.
but this authority cannot edit land records or identities.

(state authority) = now state authorities they can just verify that this land is verified.
this authority cannot edit or change land records. 

4.Core Domain Concepts

LandParcel = this is a unique land  number alloted to every land by govt and its not changed never.

OwnershipClaim = any claiment  can submit his claim with documents that contains previous owner, when bought, amount of transaction, bank records, etc.

ConsentRecord = concent record of the owner and seller with their documents for verification of rightful owner.

RegistryEntry = if the land ownership transfers from one person to another RegistryEntry is a document that keeps track of who is buying and who is selling.
Meaning only. No fields.

Now these concepts exist to prevent document forging and we can identify who is the owner of the land 
without them there will be disputes.
these documents alone does not guarantee that person is owner but it supports in a small way there are multiple documents authorities involved to prove ownership of the land.

5.Domain Actions

Submit claim any claiment can submit his claim with documents that contains previous owner, when bought, amount of transaction, bank records, etc.

who is allowed to initiate process of rightful owner = its decided by matching previous owner and this owner bank statements of transaction money of land , date and time when its bought name of previous owner and current owner also some other authorities like gram panchayat / sabha etc.

(Verify / reject) gram panchat / sabha can reject or verify the claim based on documents both from seller and buyer.

Dispute any dispute can be resolved with verfication of gram sabha / pachayat badge and verification of documents of rightful ownership claim

Transfer
Preconditions + outcomes.
Any claim on land which does not have documents and support of authorities with proper documents will be rejected.
the outcome consists of less fake ownership claims, less documents forging etc.
preconditions consistes of proper documents proper authorities support for a claim also if current owner of land and previous owner both say that land is in dispute then it will go for investigation of claim without proper documents no one can claim anyone's land.


6.Ownership lifecycle

Step-by-step, plain English
seller wants to sell
seller adds the land documents and submit to verification by gram panchayat  / sabha for selling land.
gram panchayat / sabha can verify if the selling claim is real with cross verification with documents after verification the land is for selling
buyer show their intrest in land buying.
buyer and  seller initiate with mutual concent. 
again gram panchayat / sabha verifies if the documents are correct and tranfer happens with mutual concent.

7.Blockchain boundary

Used for hash to store land record.

What is written on chain? =  Every document is written as hash number on chain if anyone tries to change or edit document then hash changes from previous to new and new hash is added but previous hash will not be deleted.

When is it written? = when new documents and new ownership is approved by authorities and both sides of buyer and seller then its stored in hash and that cannot be edited removed in any case because if later conflicat arises which is highly impossible there will be no way anyone can delete those documents change names etc.

What guarantee does that give? = No false ownership will be there is these things are implemented because once there is no way to edit documents once they are uploaded and a hash is formed there is no way anyone can forge documents and get away with it.

documents cannot be uploaded directly on blockchain but hash can so we store hash on blockchain if document changes hash changes and new hash added but old one is still stored nothing can be edited and deleted.


8.Failure scenarios 

(Conflicting claims) these can be created if (gram panchayat / sabha) will verify the record of land claim also the seller and buyer both should sumbit documents which will be revived if the conflicat arises.

(Authority abuse) What happens when abuse is suspected? =  gram panchayat / sabha is not a one person that can do anything its a bunch of people also this authority has only the power to add a verification badge not to change ownership of land so there is no hope that a authority can do this also if its done the current owner and previous owner can challenge the claim and show the problem BhumiLedger solves major problem of adding less authority of single person in this and make all this available for people to see so they can see what documents are submitted , how much does the land cost, bank statements that will help if any problems arrives so there is no way there will be authority abuse.

What is recorded? = Everything current owner, previous owner, transaction record of the land ownership transfer , Registry papers, name of owners from start to end , date , which court and which lawyer make those documents and who was present as witness of transactions their names.

What cannot be erased? = If a ownership is verified by both sides and authorities that ownership can be challenged but the documents and ownership of anyone in any case cannot be erased previous owner any type of document that support ownership will not be removed 
 current owner, previous owner, transaction record of the land ownership transfer , Registry papers, name of owners from start to end , date , which court and which lawyer make those documents and who was present as witness of transactions their names.

 
(Forged evidence) forged evidence is a threat but its a lot of work because there are multiple authorities involved no one can convince multiple authorities for document forging and also current and previous owner who has previous ownership can submit a investigation if they are unsatisfied with current owner factors depends on problems but its highly impossible for one person to do this when there is no way to delete and edit documents and there is no way anyone can delete previous owners.
