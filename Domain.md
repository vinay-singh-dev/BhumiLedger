1.Purpose:
the problem is land records being kept private.

BhumiLedger exists so the documents cannot be edited or removed once entered.

this reduces risks of documents forging , fake claims etc.

2. Core Principles 
(Append-only history) it means that past records of land owners are kept and they cannot be removed only new owners can be  added.
(Verified records immutable) records cannot me modified or edited only new records can be kept.

3.Actors

Community member = community members can only see records and become claiment for a land after record verification decision.
community members cannot verify claim.
community memebrs cannot transfer ownership alone they need authorities.
community members cannot delete or edit history its up to authorities.

(Gram Sabha / Panchayat) = these authorities they can approve/Reject a claim of a claiment after record verification decision.
these are authorities they cannot create a claim.
these authorities cannot transfer ownership.
these authorities cannot delete or edit history.
these authorities can only review documents and verify a claim or unverify it based on documents.

(State authority) now state authorities they can just Approve/Reject that this land is verified after gram panchayat / sabha verification.
these authorities cannot creat a claim.
these authorities cannot transfer ownership.
these authorities cannot delet or edit history.

Auditor
For each: allowed + forbidden actions
(gram sabha / panchayat) this authority can add a verification if the land is not any dispute after record verification decision.
but this authority cannot edit land records or identities.

(state authority) = now state authorities they can just verify that this land is verified by gram panchayat / sabha after record verification decision.
this authority cannot edit or change land records. 

4.Core Domain Concepts

LandParcel = A uniquely identifiable unit of land within the system, independent of ownership. 

OwnershipClaim = An assertion of rights over a LandParcel, pending verification.

ConsentRecord = consent record of the owner and seller with their documents for verification of rightful owner.

RegistryEntry = if the land ownership transfers from one person to another RegistryEntry is a document that keeps track of who is buying and who is selling.
Meaning only. No fields.

Now these concepts exist to prevent document forging and we can identify who is the owner of the land 
without them there will be disputes.
these documents alone does not guarantee that person is owner but it supports in a small way there are multiple documents authorities involved to prove ownership of the land.

5.Domain Actions

Submit claim = any claiment can submit his claim with documents

who is allowed to initiate process of claiment = Any claimant may submit an OwnershipClaim, which has no legal standing until verified.

(Verify / reject) gram panchat / sabha can reject or verify the claim based on documents both from seller and buyer.

Dispute any dispute can be resolved with verfication of gram sabha / pachayat badge and verification of documents of rightful ownership claim

Transfer
Preconditions + outcomes.
Any claim on land which does not have documents and support of authorities with proper documents will be rejected.
the outcome consists of less fake ownership claims, less documents forging etc.
preconditions consistes of proper documents proper authorities support for a claim also if current owner of land and previous owner both say that land is in dispute then it will go for investigation of claim without proper documents no one can claim anyone's land.


6.Ownership lifecycle

Step-by-step, plain English
owner transfers ownership.
owner adds the land documents and submit to verification by gram panchayat  / sabha.
gram panchayat / sabha can verify if the claim is real with cross verification with documents.
ownership transfers with mutual concent.
transfer intent recorded.
again gram panchayat / sabha verifies if the documents are correct and tranfer happens with mutual concent.

7.Blockchain boundary

Used for hash to store land record.

What is written on chain? =  Every document is written as hash number on chain if anyone tries to change or edit document then hash changes from previous to new and new hash is added but previous hash will not be deleted.

When is it written? = when new documents and new ownership is approved by authorities and both sides of buyer and seller then its stored in hash and that cannot be edited removed because if later conflicat arises it Provides tamper-evident history of those documents.

What guarantee does that give? = this guarantees that ownership will be protected by blockchain and every change will be recorded any editing in documents will be records and saved.

it detects every change that will happen.

documents cannot be uploaded directly on blockchain but hash can so we store hash on blockchain if document changes hash changes and new hash added but old one is still stored nothing can be edited and deleted.


8.Failure scenarios 

(Conflicting claims) these can be created if (gram panchayat / sabha) will verify the record of land claim also the seller and buyer both should sumbit documents which will be revived if the conflicat arises.

(Authority abuse) What happens when abuse is suspected? = In any abuse the hash will change if documents will be edited it Provides tamper-evident history of those documents.

What is recorded? = Everything current owner, previous owner, transaction record of the land ownership transfer , Registry papers, name of owners from start to end , date , which court and which lawyer make those documents and who was present as witness of transactions their names.

What cannot be erased? = Any ownership and documets cannot be edited or erased because all of this is stored in hash if anything changes hash changes it provides tamper-evident history of those documents.
