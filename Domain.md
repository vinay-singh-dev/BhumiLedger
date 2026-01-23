1.Purpose:
BhumiLedger solves the problem of land records being manipulated by govt. officials
It does not solve the corruption and bribes taken in land selling.

2. Core Principles (invariants)
Append-only history
Verified records immutable
No silent authority overrides
External records = evidence, not truth

3.Actors

Community member

Gram Sabha / Panchayat

State authority

Auditor
For each: allowed + forbidden actions

4.Core Domain Concepts

LandParcel

OwnershipClaim

ConsentRecord

RegistryEntry
Meaning only. No fields.

5.Domain Actions

Submit claim

Verify / reject

Dispute

Transfer
Preconditions + outcomes.

6.Ownership lifecycle

Step-by-step, plain English

7.Blockchain boundary

Used for

NOT used for

8.Failure scenarios

Conflicting claims

Authority abuse

Forged evidence
